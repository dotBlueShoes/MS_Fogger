package dotBlueShoes.fogger.mixin.mixins;

import dotBlueShoes.fogger.Fogger;
import dotBlueShoes.fogger.Manager;
import dotBlueShoes.fogger.utility.FogColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(
	value = EntityPlayerSP.class,
	remap = false
)
public abstract class EntityPlayerSPMixin extends EntityPlayer {

	@Shadow private Minecraft mc;

	public EntityPlayerSPMixin(World world) {
		super(world);
	}

	/**
	 * @author dotBlueShoes
	 * @reason For now only.
	 */
	@Overwrite
	public void respawnPlayer() {
		this.mc.respawn(false, 0);

		if (Fogger.isFogZeroColorized) {
			final int fogDefId = Manager.findFogEffect(mc.theWorld, this);
			final int fogColorId = Fogger.fogDefinitions[fogDefId].iColor;
			final FogColor color = Fogger.fogColors[fogColorId];
			Manager.setFogZeroColor(color.r, color.g, color.b);
		}

		Manager.setFogToZero(); // RESET. So when player teleports fog comes back to normal.
	}

}
