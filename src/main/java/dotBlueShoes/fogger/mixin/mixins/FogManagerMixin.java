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
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.weather.Weather;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;

import java.nio.FloatBuffer;
import java.util.Objects;

@Mixin(
	value = FogManager.class,
	remap = false
)
public abstract class FogManagerMixin {

	//@Shadow @Final public static int FOG_MODE_SKY;
	//@Shadow @Final public static int FOG_MODE_NORMAL;
	@Shadow @Final public Minecraft mc;
	@Shadow public float fogRed;
	@Shadow public float fogGreen;
	@Shadow public float fogBlue;
	//@Shadow public float fogBrightnessOld;
	//@Shadow public float fogBrightness;
	//@Shadow @Final private FloatBuffer fogColorBuffer;
	//@Shadow public abstract void updateBrightness();
	//@Shadow public abstract void updateFogColor(float partialTick);
	@Shadow protected abstract FloatBuffer buffer(float r, float g, float b, float a);

	@Unique int iLastFogEffect = 0; // One to see whether we triggered/entered a new effect.
	@Unique int iPrevFogEffect = 0; // One to compare with the last applied effect.

	@Unique static final private long FOG_CHANGE_TIME_MAX = 5000; // 5 sec
	@Unique public long fogChangeTime;

	@Unique public float fogStart = 0.0f;
	@Unique public float fogEnd = 0.0f;
	@Unique public FogColor fogColor = FogColor.DEFAULT;

	@Unique public float lastFogStart = FogDefinition.ZERO.start;
	@Unique public float lastFogEnd = FogDefinition.ZERO.end;
	@Unique public FogColor lastFogColor = FogColor.DEFAULT;

	@Unique public void setLastFog(final float start, final float end, final FogColor color) {
		lastFogStart = start;
		lastFogEnd = end;
		lastFogColor = color;
	}

	// TODO:
	// This function/mechanism could be optimized by presetting ranges
	//  of said dimensions, weathers, biomes so we wouldn't lose time looking through all
	//  keys in fogSettings but only spend minimal time there looking at a subset.
	@Unique public int findFogEffect(final float partialTick) {

		Weather weather = this.mc.theWorld.weatherManager.getCurrentWeather();
		byte iWeather = (weather == null) ?  0 : (byte)weather.weatherId;

		byte iDimension = (byte)mc.thePlayer.dimension;

		int yPos = (int)mc.thePlayer.getPosition(partialTick).yCoord;

		int biome = this.mc.theWorld.getBlockBiome(
			(int)mc.thePlayer.getPosition(partialTick).xCoord,
			(int)mc.thePlayer.getPosition(partialTick).yCoord,
			(int)mc.thePlayer.getPosition(partialTick).zCoord
		).hashCode();

		//this.mc.theWorld.getBiomeProvider().getBiomes();
		//for (byte iBiome = 0; iBiome < Registries.BIOMES.size(); ++iBiome) {
		//	Biome biome = Registries.BIOMES.getItemByNumericId(iBiome);
		//	if (playerBiome.translationKey.equals(biome.translationKey)) {
		//		Fogger.LOGGER.info("Biome Id: {}", iBiome);
		//	}
		//}

		//for (Biome biome : Registries.BIOMES) {
		//	//biome
		//}

		for (int i = 0; i < Fogger.fogSettings.length; ++i) {
			final FogSetting setting = Fogger.fogSettings[i];

			//Fogger.LOGGER.info("Biome: {}, PBiome {}", biome, setting.biome);

			boolean isEffect =
				setting.world <= iDimension &&
				setting.weather <= iWeather &&
				setting.yLevel <= yPos &&
				(setting.biome == biome || setting.biome == 0); // if not found refer to default biome.

			if (isEffect) {
				Fogger.LOGGER.info("Fog: {}, Setting: {}", setting.iFogDefinition, i);
				return setting.iFogDefinition;
			}
		}

		return 1; // FogDefinition.DEFAULT
	}

	@Unique public void applyFogEffect(
		final int iCurrentFogEffect
	) {
		FogDefinition currentFogEffect = Fogger.fogDefinitions[iCurrentFogEffect];

		if (iCurrentFogEffect != iPrevFogEffect) {

			//Fogger.LOGGER.info("cur: {}", iCurrentFogEffect);
			//Fogger.LOGGER.info("pre: {}", iPrevFogEffect);
			//Fogger.LOGGER.info("lst: {}", iLastFogEffect);

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

		GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);

		if (duration > FOG_CHANGE_TIME_MAX) { // Apply full currentFogEffect.
			fogStart = currentFogEffect.start;
			fogEnd = currentFogEffect.end;
			// This also means that this changes when we're 100% one effect.
			iLastFogEffect = iCurrentFogEffect;
		} else { // Apply a mix of current and previous effect.

			final float newLerp = duration / FOG_CHANGE_TIME_MAX;
			final float oldLerp = 1.0f - newLerp;

			fogStart = (currentFogEffect.start * newLerp) + (lastFogStart * oldLerp);
			fogEnd = (currentFogEffect.end * newLerp) + (lastFogEnd * oldLerp);

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

		GL11.glFog(GL11.GL_FOG_COLOR, this.buffer(this.fogRed, this.fogGreen, this.fogBlue, 0.5F));
		GL11.glNormal3f(0.0F, -1.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		final boolean isCameraPhotoMode = this.mc.currentScreen instanceof GuiPhotoMode;
		final boolean isCameraInWater = CameraUtil.isUnderLiquid(this.mc.activeCamera, this.mc.theWorld, Material.water, partialTick);
		final boolean isCameraInLava = CameraUtil.isUnderLiquid(this.mc.activeCamera, this.mc.theWorld, Material.lava, partialTick);

		if (isCameraPhotoMode) {
			final float fogDistance = farPlaneDistance * ((GuiPhotoMode)this.mc.currentScreen).getFog(partialTick);
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
			GL11.glFogf(GL11.GL_FOG_START, fogDistance * 0.25F);
			GL11.glFogf(GL11.GL_FOG_END, fogDistance);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 1.0F);
		} else if (isCameraInWater) {
			// FOG_START, FOG_END should also be specified here.
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 0.1F);
		} else if (isCameraInLava) {
			// FOG_START, FOG_END should also be specified here.
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 2.0F);
		} else {
			final float maxFogDistance = (float) (renderDistance.chunks * 16);
			int iCurrentFogEffect = findFogEffect(partialTick);
			applyFogEffect(iCurrentFogEffect);

			GL11.glFogf(GL11.GL_FOG_START, maxFogDistance * fogStart);
			GL11.glFogf(GL11.GL_FOG_END, maxFogDistance * fogEnd);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 1.0F);

			// TODO: This needs fixing later. prob.
			//if (fogMode == -1) { // -1 STANDS FOR [SKY RENDER]
			//	Fogger.LOGGER.info("FogMode == -1!");
			//	GL11.glFogf(GL11.GL_FOG_START, 0.0F);
			//	GL11.glFogf(GL11.GL_FOG_END, 0.25F);
			//	//GL11.glFogf(GL11.GL_FOG_END, fogDistance * 0.8F);
			//}

			if (OpenGLHelper.enableSphericalFog) {
				//GL11.glFogi(GL11.GL_FOG_DISTANCE_MODE_NV, GL11.GL_EYE_RADIAL_NV);
				GL11.glFogi(34138, 34139);
			}
		}

		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT);
	}

}
