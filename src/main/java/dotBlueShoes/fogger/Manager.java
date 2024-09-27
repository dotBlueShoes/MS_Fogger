package dotBlueShoes.fogger;

import dotBlueShoes.fogger.utility.FogColor;
import dotBlueShoes.fogger.utility.FogDefinition;
import dotBlueShoes.fogger.utility.FogSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.core.world.weather.Weather;

public class Manager {

	public static int iLastFogEffect = 0; // One to see whether we triggered/entered a new effect.
	public static int iPrevFogEffect = 0; // One to compare with the last applied effect.

	public static final long FOG_CHANGE_TIME_MAX = 8000; // 4 sec
	public static long fogChangeTime;

	public static float fogStart = 0.0f;
	public static float fogEnd = 0.0f;
	public static float fogDensity = 1.0f;
	public static FogColor fogColor = new FogColor(FogColor.ZERO); // that's a cpy!

	public static float lastFogStart = FogDefinition.ZERO.start;
	public static float lastFogEnd = FogDefinition.ZERO.end;
	public static FogColor lastFogColor = new FogColor(FogColor.ZERO); // that's a cpy!


	public static int findFogEffect(World world, Entity entity) {

		int season = world.seasonManager.getCurrentSeason().hashCode();
		// long means: 24000 - day, 168000 - week, 192000 - lunar cycle - 8 phases (there's no lunar cycles tho)
		long time = world.getWorldTime() % 168000;

		Weather weather = world.weatherManager.getCurrentWeather();
		int iWeather = (weather == null) ?  0 : weather.weatherId;
		int iDimension = world.dimension.id;

		int yPos = (int)entity.y;

		int biome = world.getBlockBiome(
			(int)entity.x,
			(int)entity.y,
			(int)entity.z
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


	public static void setFogZeroColor(float r, float g, float b) {
		Fogger.fogColors[0].r = r;
		Fogger.fogColors[0].g = g;
		Fogger.fogColors[0].b = b;
	}

	public static void setLastFog(final float start, final float end, final FogColor color) {
		lastFogStart = start;
		lastFogEnd = end;
		lastFogColor.r = color.r;
		lastFogColor.g = color.g;
		lastFogColor.b = color.b;
	}

	public static void darkenColorByCelestialAngle(World world, final float partialTick) {
		float dayProgress = MathHelper.cos(world.getCelestialAngle(partialTick) * 3.1415927F * 2.0F) * 2.0F + 0.5F;
		dayProgress = MathHelper.clamp(dayProgress, 0.0F, 1.0F);
		fogColor.r *= dayProgress;
		fogColor.g *= dayProgress;
		fogColor.b *= dayProgress;
	}

	public static void setFogToZero() {
		iLastFogEffect = 0;
		iPrevFogEffect = 0;
	}

}
