package dotBlueShoes.fogger.mixin.mixins;

import dotBlueShoes.fogger.Fogger;
import dotBlueShoes.fogger.Manager;
import dotBlueShoes.fogger.utility.FogColor;
import dotBlueShoes.fogger.utility.FogDefinition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPhotoMode;
import net.minecraft.client.option.enums.RenderDistance;
import net.minecraft.client.render.FogManager;
import net.minecraft.client.render.OpenGLHelper;
import net.minecraft.client.render.camera.CameraUtil;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;

import java.nio.FloatBuffer;

@Mixin(
	value = FogManager.class,
	remap = false
)
public abstract class FogManagerMixin {

	@Unique final int GL11_GL_FOG_DISTANCE_MODE_NV = 34138;
	@Unique final int GL11_GL_EYE_RADIAL_NV = 34139;

	@Shadow @Final public Minecraft mc;
	@Shadow protected abstract FloatBuffer buffer(float r, float g, float b, float a);

	@Unique public void setupFogEffect(
		final float partialTick
	) {
		final int iCurrentFogEffect = Manager.findFogEffect(this.mc.theWorld, this.mc.thePlayer);
		final FogDefinition currentFogEffect = Fogger.fogDefinitions[iCurrentFogEffect];
		final FogColor currentColor = Fogger.fogColors[currentFogEffect.iColor];

		// INSTANT Setting. (aka no blend)
		Manager.iPrevFogEffect = iCurrentFogEffect;
		Manager.iLastFogEffect = iCurrentFogEffect;

		Manager.fogStart = currentFogEffect.start;
		Manager.fogEnd = currentFogEffect.end;
		Manager.fogColor.r = currentColor.r;
		Manager.fogColor.g = currentColor.g;
		Manager.fogColor.b = currentColor.b;

		if (Fogger.isFogAutoDarkenByNightSky) Manager.darkenColorByCelestialAngle(this.mc.theWorld, partialTick);
	}

	@Unique public void setupFogEffectBlend(
		final float partialTick
	) {
		final int iCurrentFogEffect = Manager.findFogEffect(this.mc.theWorld, this.mc.thePlayer);
		final FogDefinition currentFogEffect = Fogger.fogDefinitions[iCurrentFogEffect];
		final FogColor currentColor = Fogger.fogColors[currentFogEffect.iColor];

		if (iCurrentFogEffect != Manager.iPrevFogEffect) {

			// If during last change we didn't hit FOG_CHANGE_TIME_MAX
			// Then store values from previous blend as last so we can always blend well.
			if (Manager.iLastFogEffect != Manager.iPrevFogEffect) {
				Manager.iLastFogEffect = Manager.iPrevFogEffect;
				Manager.setLastFog(Manager.fogStart, Manager.fogEnd, Manager.fogColor);
			} else {
				FogDefinition lfe = Fogger.fogDefinitions[Manager.iLastFogEffect];
				Manager.setLastFog(lfe.start, lfe.end, Fogger.fogColors[lfe.iColor]);
			}

			Manager.fogChangeTime = System.currentTimeMillis();
			Manager.iPrevFogEffect = iCurrentFogEffect;
		}

		final long fogCurrentTime = System.currentTimeMillis();
		final float duration = fogCurrentTime - Manager.fogChangeTime;

		if (duration > Manager.FOG_CHANGE_TIME_MAX) { // Apply full currentFogEffect.
			Manager.fogStart = currentFogEffect.start;
			Manager.fogEnd = currentFogEffect.end;

			Manager.fogColor.r = currentColor.r;
			Manager.fogColor.g = currentColor.g;
			Manager.fogColor.b = currentColor.b;

			if (Fogger.isFogAutoDarkenByNightSky) Manager.darkenColorByCelestialAngle(this.mc.theWorld, partialTick);

			Manager.iLastFogEffect = iCurrentFogEffect;
		} else { // Apply a mix of current and previous effect.

			final float newLerp = duration / Manager.FOG_CHANGE_TIME_MAX;
			final float oldLerp = 1.0f - newLerp;

			Manager.fogStart = (currentFogEffect.start * newLerp) + (Manager.lastFogStart * oldLerp);
			Manager.fogEnd = (currentFogEffect.end * newLerp) + (Manager.lastFogEnd * oldLerp);
			Manager.fogColor.r = (currentColor.r * newLerp) + (Manager.lastFogColor.r * oldLerp);
			Manager.fogColor.g = (currentColor.g * newLerp) + (Manager.lastFogColor.g * oldLerp);
			Manager.fogColor.b = (currentColor.b * newLerp) + (Manager.lastFogColor.b * oldLerp);

			if (Fogger.isFogAutoDarkenByNightSky) Manager.darkenColorByCelestialAngle(this.mc.theWorld, partialTick);

			// Ensure that fog start point cannot be higher than end point!
			Manager.fogStart = Math.min(Manager.fogStart, Manager.fogEnd);
		}

		Manager.fogDensity = 1.0F;
	}

	@Unique
	public void setupFogWater(float partialTick) {
		float red, green, blue;

		if (this.mc.gameSettings.biomeWater.value) {
			World world = this.mc.theWorld;

			int x = MathHelper.floor_double(this.mc.activeCamera.getX(partialTick));
			int z = MathHelper.floor_double(this.mc.activeCamera.getZ(partialTick));

			double temp = world.getBlockTemperature(x, z);
			double humid = world.getBlockHumidity(x, z);
			int waterColor = Colorizers.water.getColor(temp, humid);

			red = (float)(waterColor >> 16 & 255) / 255.0F;
			green = (float)(waterColor >> 8 & 255) / 255.0F;
			blue = (float)(waterColor & 255) / 255.0F;

			red = MathHelper.clamp(red, 0.0F, 1.0F);
			green = MathHelper.clamp(green, 0.0F, 1.0F);
			blue = MathHelper.clamp(blue, 0.0F, 1.0F);

			red *= 0.5F;
			green *= 0.5F;
			blue *= 0.5F;

		} else {
			red = 0.02F;
			green = 0.02F;
			blue = 0.2F;
		}

		Manager.fogColor = new FogColor(red, green, blue);
		Manager.fogDensity = 0.1F;

		if (Fogger.isFogZeroColorized) Manager.setFogZeroColor(red, green, blue);
		Manager.setFogToZero(); // RESET. So when player exits the water fog comes back to normal.
	}

	@Unique
	public void setupFogLava(float ignoredPartialTick) {
		float red, green, blue;

		red = 0.6F;
		green = 0.1F;
		blue = 0.0F;

		Manager.fogColor = new FogColor(red, green, blue);
		Manager.fogDensity = 2.0F;

		if (Fogger.isFogZeroColorized) Manager.setFogZeroColor(red, green, blue);
		Manager.setFogToZero(); // RESET. So when player exits the lava fog comes back to normal.
	}

	@Unique
	public void applyFogType(int type) {
		GL11.glFog(GL11.GL_FOG_COLOR, this.buffer(Manager.fogColor.r, Manager.fogColor.g, Manager.fogColor.b, 0.5F));
		GL11.glNormal3f(0.0F, -1.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glFogi(GL11.GL_FOG_MODE, type);
		GL11.glFogf(GL11.GL_FOG_DENSITY, Manager.fogDensity);
	}

	@Unique
	public void applyFogLinear(float fogStrength) {
		RenderDistance renderDistance = this.mc.gameSettings.renderDistance.value;
		final float maxFogDistance = (float) (renderDistance.chunks * 16);

		applyFogType(GL11.GL_LINEAR);
		GL11.glFogf(GL11.GL_FOG_START, maxFogDistance * Manager.fogStart);
		GL11.glFogf(GL11.GL_FOG_END, maxFogDistance * Manager.fogEnd * fogStrength);
	}

	@Unique
	public void applyFogPhotoMode(float farPlaneDistance, float partialTick) {
		final float maxFogDistance = farPlaneDistance * ((GuiPhotoMode)this.mc.currentScreen).getFog(partialTick);

		applyFogType(GL11.GL_LINEAR);
		GL11.glFogf(GL11.GL_FOG_START, maxFogDistance * 0.25F);
		GL11.glFogf(GL11.GL_FOG_END, maxFogDistance);
	}

	/**
	 * @author dotBlueShoes
	 * @reason The reason is. There's no reason. Contact Me.
	 */
	@Overwrite
	public void setupFog(int fogMode, float farPlaneDistance, float partialTick) {

		// This method is called from 2 places. For sky and for terrain.
		final int FOG_TYPE_SKY = -1;

		final boolean isCameraPhotoMode = this.mc.currentScreen instanceof GuiPhotoMode;
		final boolean isCameraInWater = CameraUtil.isUnderLiquid(this.mc.activeCamera, this.mc.theWorld, Material.water, partialTick);
		final boolean isCameraInLava = CameraUtil.isUnderLiquid(this.mc.activeCamera, this.mc.theWorld, Material.lava, partialTick);

		final float fogStrength = (fogMode == FOG_TYPE_SKY) ? 0.8F : 1.0f;

		// TODO: no if statements needed. just document what and why.
		if (isCameraPhotoMode)      applyFogPhotoMode(farPlaneDistance, partialTick);
		else if (isCameraInWater)   applyFogType(GL11.GL_EXP);
		else if (isCameraInLava)    applyFogType(GL11.GL_EXP);
		else                        applyFogLinear(fogStrength); // Change in behaviour! Called for sky-callee when other criteria fail.

		if (OpenGLHelper.enableSphericalFog) {
			GL11.glFogi(GL11_GL_FOG_DISTANCE_MODE_NV, GL11_GL_EYE_RADIAL_NV);
		}

		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT);
	}

	// EventFog has:
	//  - priority property
	//  - timer property (when timer expires fog ends) (-1 for infinite)
	//  - enabling and disabling mechanism (I guess a bool) (minecraft is not written in play-stop way but in rather if set do that way so why not)

	/**
	 * @author dotBlueShoes
	 * @reason The reason is. There's no reason. Contact Me.
	 */
	@Overwrite
	public void updateFogColor(float partialTick) {

		final boolean isCameraPhotoMode = this.mc.currentScreen instanceof GuiPhotoMode;
		final boolean isCameraInWater = CameraUtil.isUnderLiquid(this.mc.activeCamera, this.mc.theWorld, Material.water, partialTick);
		final boolean isCameraInLava = CameraUtil.isUnderLiquid(this.mc.activeCamera, this.mc.theWorld, Material.lava, partialTick);

		if (isCameraPhotoMode)      Manager.fogDensity = 1.0F;
		else if (isCameraInWater)   setupFogWater(partialTick);
		else if (isCameraInLava)    setupFogLava(partialTick);
		else                        setupFogEffectBlend(partialTick);

		GL11.glClearColor(Manager.fogColor.r, Manager.fogColor.g, Manager.fogColor.b, 0.0F);
	}

}
