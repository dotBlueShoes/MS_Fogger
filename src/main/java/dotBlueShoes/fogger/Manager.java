package dotBlueShoes.fogger;

import dotBlueShoes.fogger.utility.FogColor;
import dotBlueShoes.fogger.utility.FogDefinition;
import net.minecraft.client.Minecraft;
import net.minecraft.core.util.helper.MathHelper;

public class Manager {

	public static int iLastFogEffect = 0; // One to see whether we triggered/entered a new effect.
	public static int iPrevFogEffect = 0; // One to compare with the last applied effect.

	public static final long FOG_CHANGE_TIME_MAX = 10000; // 5 sec
	public static long fogChangeTime;

	public static float fogStart = 0.0f;
	public static float fogEnd = 0.0f;
	public static float fogDensity = 1.0f;
	public static FogColor fogColor = new FogColor(FogColor.ZERO); // that's a cpy!

	public static float lastFogStart = FogDefinition.ZERO.start;
	public static float lastFogEnd = FogDefinition.ZERO.end;
	public static FogColor lastFogColor = new FogColor(FogColor.ZERO); // that's a cpy!

	public static void setLastFog(final float start, final float end, final FogColor color) {
		lastFogStart = start;
		lastFogEnd = end;
		lastFogColor.r = color.r;
		lastFogColor.g = color.g;
		lastFogColor.b = color.b;
	}

	public static void darkenColorByCelestialAngle(Minecraft mc, final float partialTick) {
		float dayProgress = MathHelper.cos(mc.theWorld.getCelestialAngle(partialTick) * 3.1415927F * 2.0F) * 2.0F + 0.5F;
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
