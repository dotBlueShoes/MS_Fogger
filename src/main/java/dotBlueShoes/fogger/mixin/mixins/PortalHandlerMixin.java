package dotBlueShoes.fogger.mixin.mixins;

import dotBlueShoes.fogger.Fogger;
import dotBlueShoes.fogger.Manager;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.PortalHandler;
import net.minecraft.core.world.World;
import net.minecraft.core.world.type.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(
	value = PortalHandler.class,
	remap = false
)
public abstract class PortalHandlerMixin {

	/**
	 * @author dotBlueShoes
	 * @reason For now only.
	 */
	@Overwrite
	public boolean attemptToTeleportToClosestPortal(World world, Entity entity, Dimension oldDim, Dimension newDim) {
		int searchRadius = 128;
		double lowestEntityDistanceSquared = -1.0;
		int closestPortalX = 0;
		int closestPortalY = 0;
		int closestPortalZ = 0;
		int entityBlockX = MathHelper.floor_double(entity.x);
		int entityBlockZ = MathHelper.floor_double(entity.z);
		WorldType oldWorldType = oldDim.getDimensionData(world).getWorldType();
		WorldType newWorldType = newDim.getDimensionData(world).getWorldType();
		double entityPosYScaled = entity.y;
		int oldDimRangeY = oldWorldType.getMaxY() - oldWorldType.getMinY();
		entityPosYScaled -= (double)oldWorldType.getMinY();
		entityPosYScaled /= (double)oldDimRangeY;
		int newDimRangeY = newWorldType.getMaxY() - newWorldType.getMinY();
		entityPosYScaled *= (double)newDimRangeY;
		entityPosYScaled += (double)newWorldType.getMinY();
		int targetPortalId;
		if (newDim.homeDim == null) {
			targetPortalId = oldDim.portalBlockId;
		} else {
			targetPortalId = newDim.portalBlockId;
		}

		int dx;
		double newEntityZ;
		for(dx = entityBlockX - searchRadius; dx <= entityBlockX + searchRadius; ++dx) {
			double xEntityDistance = (double)dx + 0.5 - entity.x;

			for(int dz = entityBlockZ - searchRadius; dz <= entityBlockZ + searchRadius; ++dz) {
				double zEntityDistance = (double)dz + 0.5 - entity.z;

				for(int dy = newWorldType.getMaxY() - 1; dy >= newWorldType.getMinY(); --dy) {
					if (world.getBlockId(dx, dy, dz) == targetPortalId) {
						while(world.getBlockId(dx, dy - 1, dz) == targetPortalId) {
							--dy;
						}

						newEntityZ = (double)dy + 0.5 - entityPosYScaled;
						double entityDistanceSquared = xEntityDistance * xEntityDistance + newEntityZ * newEntityZ + zEntityDistance * zEntityDistance;
						if (lowestEntityDistanceSquared < 0.0 || entityDistanceSquared < lowestEntityDistanceSquared) {
							lowestEntityDistanceSquared = entityDistanceSquared;
							closestPortalX = dx;
							closestPortalY = dy;
							closestPortalZ = dz;
						}
					}
				}
			}
		}

		if (lowestEntityDistanceSquared >= 0.0) {
			dx = closestPortalX;
			int portalZ = closestPortalZ;
			double newEntityX = (double)dx + 0.5;
			double newEntityY = (double)closestPortalY + 0.5;
			newEntityZ = (double)portalZ + 0.5;
			if (world.getBlockId(dx - 1, closestPortalY, portalZ) == targetPortalId) {
				newEntityX -= 0.5;
			}

			if (world.getBlockId(dx + 1, closestPortalY, portalZ) == targetPortalId) {
				newEntityX += 0.5;
			}

			if (world.getBlockId(dx, closestPortalY, portalZ - 1) == targetPortalId) {
				newEntityZ -= 0.5;
			}

			if (world.getBlockId(dx, closestPortalY, portalZ + 1) == targetPortalId) {
				newEntityZ += 0.5;
			}

			// HERE - TODO: make nether set an instant fog effect.
			// 1. setupFogEffect() has to be outside mixin to access it from here.
			// 2. Partial Tick is needed.
			//setupFogEffect(0);
			Manager.setFogToZero(); // RESET. So when player teleports fog comes back to normal.
			//Fogger.LOGGER.info("dim: {}", world.dimension);

			entity.moveTo(newEntityX, newEntityY - 0.5, newEntityZ, entity.yRot, 0.0F);
			entity.xd = entity.yd = entity.zd = 0.0;



			return true;
		} else {
			return false;
		}
	}

}
