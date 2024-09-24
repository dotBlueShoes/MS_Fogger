package dotBlueShoes.fogger.mixin.mixins;

import net.minecraft.core.world.World;
import net.minecraft.core.world.type.WorldTypeNether;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(
	value = WorldTypeNether.class,
	remap = false
)
public abstract class WorldTypeNetherMixin {

	/**
	 * @author dotBlueShoes
	 * @reason Because weirdly Nether's sun is placed at 0.5f and not 1.0f. Making Fog really dark unnecessary.
	 */
	@Overwrite
	public float getCelestialAngle(World world, long tick, float partialTick) {
		return 1.0F;
	}
}
