package dotBlueShoes.fogger.mixin.mixins;

import dotBlueShoes.fogger.Fogger;
import dotBlueShoes.fogger.utility.FogColor;
import dotBlueShoes.fogger.utility.FogDefinition;
import dotBlueShoes.fogger.utility.FogSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPhotoMode;
import net.minecraft.client.option.enums.RenderDistance;
import net.minecraft.client.render.FogManager;
import net.minecraft.client.render.OpenGLHelper;
import net.minecraft.client.render.camera.CameraUtil;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.weather.Weather;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;

import java.nio.FloatBuffer;

@Mixin(
	value = FogManager.class,
	remap = false
)
public abstract class FogManagerMixin {

	@Shadow @Final public Minecraft mc;
	@Shadow public float fogRed;
	@Shadow public float fogGreen;
	@Shadow public float fogBlue;
	@Shadow protected abstract FloatBuffer buffer(float r, float g, float b, float a);

	@Unique int iLastFogEffect = 0; // One to see whether we triggered/entered a new effect.
	@Unique int iPrevFogEffect = 0; // One to compare with the last applied effect.

	@Unique static final private long FOG_CHANGE_TIME_MAX = 10000; // 5 sec
	@Unique public long fogChangeTime;

	@Unique public float fogStart = 0.0f;
	@Unique public float fogEnd = 0.0f;
	@Unique public FogColor fogColor = new FogColor(FogColor.ZERO); // that's a cpy!

	@Unique public float lastFogStart = FogDefinition.ZERO.start;
	@Unique public float lastFogEnd = FogDefinition.ZERO.end;
	@Unique public FogColor lastFogColor = new FogColor(FogColor.ZERO); // that's a cpy!

	@Unique public void setLastFog(final float start, final float end, final FogColor color) {
		lastFogStart = start;
		lastFogEnd = end;
		lastFogColor.r = color.r;
		lastFogColor.g = color.g;
		lastFogColor.b = color.b;
	}

	// TODO:
	// This function/mechanism could be optimized by presetting ranges
	//  of said dimensions, weathers, biomes so we wouldn't lose time looking through all
	//  keys in fogSettings but only spend minimal time there looking at a subset.
	@Unique public int findFogEffect(final float partialTick) {

		int season = this.mc.theWorld.seasonManager.getCurrentSeason().hashCode();

		// long means: 24000 - day, 168000 - week, 192000 - lunar cycle - 8 phases (there's no lunar cycles tho)
		long time = this.mc.theWorld.getWorldTime() % 168000;

		Weather weather = this.mc.theWorld.weatherManager.getCurrentWeather();
		byte iWeather = (weather == null) ?  0 : (byte)weather.weatherId;

		byte iDimension = (byte)mc.thePlayer.dimension;

		int yPos = (int)mc.thePlayer.getPosition(partialTick).yCoord;

		int biome = this.mc.theWorld.getBlockBiome(
			(int)mc.thePlayer.getPosition(partialTick).xCoord,
			(int)mc.thePlayer.getPosition(partialTick).yCoord,
			(int)mc.thePlayer.getPosition(partialTick).zCoord
		).hashCode();

		for (int i = 0; i < Fogger.fogSettings.length; ++i) {
			final FogSetting setting = Fogger.fogSettings[i];

			boolean isEffect =
				setting.world <= iDimension &&
				(setting.season == season || setting.season == 0) && // if not found refer to default (global) season.
				setting.weather <= iWeather &&
				setting.time <= time &&
				(setting.biome == biome || setting.biome == 0) && // if not found refer to default (global) biome.
				setting.yLevel <= yPos;

			if (isEffect) {
				//Fogger.LOGGER.info("Fog: {}, Setting: {}", setting.iFogDefinition, i);
				return setting.iFogDefinition;
			}
		}

		return 1; // FogDefinition.DEFAULT
	}

	@Unique public void darkenColorByCelestialAngle(final float partialTick) {
		float dayProgress = MathHelper.cos(this.mc.theWorld.getCelestialAngle(partialTick) * 3.1415927F * 2.0F) * 2.0F + 0.5F;
		dayProgress = MathHelper.clamp(dayProgress, 0.0F, 1.0F);
		fogColor.r *= dayProgress;
		fogColor.g *= dayProgress;
		fogColor.b *= dayProgress;
	}

	@Unique public void applyFogEffect(
		final int iCurrentFogEffect,
		final float partialTick
	) {
		final FogDefinition currentFogEffect = Fogger.fogDefinitions[iCurrentFogEffect];
		final FogColor currentColor = Fogger.fogColors[currentFogEffect.iColor];

		if (iCurrentFogEffect != iPrevFogEffect) {

			// If during last change we didn't hit FOG_CHANGE_TIME_MAX
			// Then store values from previous blend as last so we can always blend well.
			if (iLastFogEffect != iPrevFogEffect) {
				iLastFogEffect = iPrevFogEffect;
				setLastFog(fogStart, fogEnd, fogColor);
			} else {
				FogDefinition lfe = Fogger.fogDefinitions[iLastFogEffect];
				setLastFog(lfe.start, lfe.end, Fogger.fogColors[lfe.iColor]);
			}

			fogChangeTime = System.currentTimeMillis();
			iPrevFogEffect = iCurrentFogEffect;
		}

		final long fogCurrentTime = System.currentTimeMillis();
		final float duration = fogCurrentTime - fogChangeTime;

		if (duration > FOG_CHANGE_TIME_MAX) { // Apply full currentFogEffect.
			fogStart = currentFogEffect.start;
			fogEnd = currentFogEffect.end;

			fogColor.r = currentColor.r;
			fogColor.g = currentColor.g;
			fogColor.b = currentColor.b;

			if (Fogger.isFogAutoDarkenByNightSky) darkenColorByCelestialAngle(partialTick);

			iLastFogEffect = iCurrentFogEffect;
		} else { // Apply a mix of current and previous effect.

			final float newLerp = duration / FOG_CHANGE_TIME_MAX;
			final float oldLerp = 1.0f - newLerp;

			fogStart = (currentFogEffect.start * newLerp) + (lastFogStart * oldLerp);
			fogEnd = (currentFogEffect.end * newLerp) + (lastFogEnd * oldLerp);
			fogColor.r = (currentColor.r * newLerp) + (lastFogColor.r * oldLerp);
			fogColor.g = (currentColor.g * newLerp) + (lastFogColor.g * oldLerp);
			fogColor.b = (currentColor.b * newLerp) + (lastFogColor.b * oldLerp);

			if (Fogger.isFogAutoDarkenByNightSky) darkenColorByCelestialAngle(partialTick);

			// Ensure that fog start point cannot be higher than end point!
			fogStart = Math.min(fogStart, fogEnd);
		}
	}

	/**
	 * @author dotBlueShoes
	 * @reason The reason is. There's no reason. Contact Me.
	 */
	@Overwrite
	public void setupFog(int fogMode, float farPlaneDistance, float partialTick) {

		RenderDistance renderDistance = this.mc.gameSettings.renderDistance.value;

		final boolean isCameraPhotoMode = this.mc.currentScreen instanceof GuiPhotoMode;
		final boolean isCameraInWater = CameraUtil.isUnderLiquid(this.mc.activeCamera, this.mc.theWorld, Material.water, partialTick);
		final boolean isCameraInLava = CameraUtil.isUnderLiquid(this.mc.activeCamera, this.mc.theWorld, Material.lava, partialTick);

		// TODO
		// Why if's? This should be just 5 different methods each for said draw...

		if (isCameraPhotoMode) {
			final float fogDistance = farPlaneDistance * ((GuiPhotoMode)this.mc.currentScreen).getFog(partialTick);
			GL11.glFog(GL11.GL_FOG_COLOR, this.buffer(this.fogRed, this.fogGreen, this.fogBlue, 0.5F));
			GL11.glNormal3f(0.0F, -1.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
			GL11.glFogf(GL11.GL_FOG_START, fogDistance * 0.25F);
			GL11.glFogf(GL11.GL_FOG_END, fogDistance);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 1.0F);
		} else if (isCameraInWater) {
			// FOG_START, FOG_END should also be specified here.
			GL11.glFog(GL11.GL_FOG_COLOR, this.buffer(this.fogRed, this.fogGreen, this.fogBlue, 0.5F));
			GL11.glNormal3f(0.0F, -1.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 0.1F);
		} else if (isCameraInLava) {
			// FOG_START, FOG_END should also be specified here.
			GL11.glFog(GL11.GL_FOG_COLOR, this.buffer(this.fogRed, this.fogGreen, this.fogBlue, 0.5F));
			GL11.glNormal3f(0.0F, -1.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 2.0F);
		} else {

			final int FOG_TYPE_SKY = -1;

			if (fogMode == FOG_TYPE_SKY) {

				final float maxFogDistance = (float) (renderDistance.chunks * 16);
				int iCurrentFogEffect = findFogEffect(partialTick);
				applyFogEffect(iCurrentFogEffect, partialTick);

				GL11.glFog(GL11.GL_FOG_COLOR, this.buffer(0, 0, 0, 0.5F));
				GL11.glNormal3f(0.0F, -1.0F, 0.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
				GL11.glFogf(GL11.GL_FOG_START, maxFogDistance * fogStart);
				GL11.glFogf(GL11.GL_FOG_END, maxFogDistance * fogEnd * 0.8F);

			} else {

				final float maxFogDistance = (float) (renderDistance.chunks * 16);
				int iCurrentFogEffect = findFogEffect(partialTick);
				applyFogEffect(iCurrentFogEffect, partialTick);

				GL11.glFog(GL11.GL_FOG_COLOR, this.buffer(fogColor.r, fogColor.g, fogColor.b, 0.5F));
				GL11.glNormal3f(0.0F, -1.0F, 0.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
				GL11.glFogf(GL11.GL_FOG_START, maxFogDistance * fogStart);
				GL11.glFogf(GL11.GL_FOG_END, maxFogDistance * fogEnd);

			}

			GL11.glFogf(GL11.GL_FOG_DENSITY, 1.0F);

			if (OpenGLHelper.enableSphericalFog) {
				//GL11.glFogi(GL11.GL_FOG_DISTANCE_MODE_NV, GL11.GL_EYE_RADIAL_NV);
				GL11.glFogi(34138, 34139);
			}
		}

		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT);
	}

	/**
	 * @author dotBlueShoes
	 * @reason The reason is. There's no reason. Contact Me.
	 */
	@Overwrite
	public void updateFogColor(float partialTick) {
		GL11.glClearColor(fogColor.r, fogColor.g, fogColor.b, 0.0F);
	}

}
