package com.fusionflux.chromaticcurrents.mixin;

import com.fusionflux.chromaticcurrents.block.ColoredRedstoneWire;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneWireBlock.class)
public class RedstoneWireMixin {

	@Inject(at = @At("HEAD"), method = "connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z",cancellable = true)
	private static void connectsTo(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> cir) {
		if(state.getBlock() instanceof ColoredRedstoneWire){
			cir.setReturnValue(false);
		}
	}

}
