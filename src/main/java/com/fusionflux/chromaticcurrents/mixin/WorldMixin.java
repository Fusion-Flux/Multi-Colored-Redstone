package com.fusionflux.chromaticcurrents.mixin;

import com.fusionflux.chromaticcurrents.block.ColoredRedstoneWire;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {

	@Shadow public abstract BlockState getBlockState(BlockPos pos);

	@Shadow @Final private static Direction[] DIRECTIONS;

	@Inject(at = @At("HEAD"), method = "getEmittedRedstonePower",cancellable = true)
	public void getEmittedRedstonePower(BlockPos pos, Direction direction, CallbackInfoReturnable<Integer> cir) {
		BlockState blockStateTest = this.getBlockState(pos.offset(direction.getOpposite()));

		if(blockStateTest.getBlock() instanceof ColoredRedstoneWire) {

			if(!(this.getBlockState(pos).getBlock() instanceof AbstractRedstoneGateBlock) && this.getBlockState(pos).getBlock() != Blocks.LEVER&& this.getBlockState(pos).getBlock() != Blocks.REDSTONE_TORCH&& this.getBlockState(pos).getBlock() != Blocks.REDSTONE_BLOCK
					&& this.getBlockState(pos).getBlock() != Blocks.OBSERVER && !(this.getBlockState(pos).getBlock() instanceof ButtonBlock) && !(this.getBlockState(pos).getBlock() instanceof AbstractPressurePlateBlock))
			for (Direction upoffsetcheck : Direction.Type.HORIZONTAL) {
				if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() instanceof ColoredRedstoneWire) {
					if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() != blockStateTest.getBlock()) {
						cir.setReturnValue(0);
					}
				} else if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() instanceof RedstoneWireBlock) {
					cir.setReturnValue(0);
				}
			}

			if(!(this.getBlockState(pos).getBlock() instanceof AbstractRedstoneGateBlock) && this.getBlockState(pos).getBlock() != Blocks.LEVER&& this.getBlockState(pos).getBlock() != Blocks.REDSTONE_TORCH&& this.getBlockState(pos).getBlock() != Blocks.REDSTONE_BLOCK
					&& this.getBlockState(pos).getBlock() != Blocks.OBSERVER && !(this.getBlockState(pos).getBlock() instanceof ButtonBlock) && !(this.getBlockState(pos).getBlock() instanceof AbstractPressurePlateBlock))
			if (this.getBlockState(pos.offset(direction)).getBlock() instanceof ColoredRedstoneWire) {
				if (this.getBlockState(pos.offset(direction)).getBlock() != blockStateTest.getBlock()) {
					cir.setReturnValue(0);
				}
			} else if (this.getBlockState(pos.offset(direction)).getBlock() instanceof RedstoneWireBlock) {
				cir.setReturnValue(0);
			}
			if(direction!= Direction.UP){
			if (this.getBlockState(pos.up()).getBlock() instanceof ColoredRedstoneWire) {
				if (this.getBlockState(pos.up()).getBlock() != blockStateTest.getBlock()) {
					cir.setReturnValue(0);
				}
			} else if (this.getBlockState(pos.up()).getBlock() instanceof RedstoneWireBlock) {
				cir.setReturnValue(0);
			}
		}else{
			for (Direction upoffsetcheck : Direction.Type.HORIZONTAL) {
				if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() instanceof ColoredRedstoneWire) {
					if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() != blockStateTest.getBlock()) {
						cir.setReturnValue(0);
					}
				} else if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() instanceof RedstoneWireBlock) {
					cir.setReturnValue(0);
				}
			}
		}

			if(direction!= Direction.DOWN){
			if (this.getBlockState(pos.down()).getBlock() instanceof ColoredRedstoneWire) {
				if (this.getBlockState(pos.down()).getBlock() != blockStateTest.getBlock()) {
					cir.setReturnValue(0);
				}
			} else if (this.getBlockState(pos.down()).getBlock() instanceof RedstoneWireBlock) {
				cir.setReturnValue(0);
			}			}else{
			for (Direction upoffsetcheck : Direction.Type.HORIZONTAL) {
				if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() instanceof ColoredRedstoneWire) {
					if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() != blockStateTest.getBlock()) {
						cir.setReturnValue(0);
					}
				} else if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() instanceof RedstoneWireBlock) {
					cir.setReturnValue(0);
				}
			}
		}
		}


		if(blockStateTest.getBlock() instanceof RedstoneWireBlock) {
			if(!(this.getBlockState(pos).getBlock() instanceof AbstractRedstoneGateBlock) && this.getBlockState(pos).getBlock() != Blocks.LEVER&& this.getBlockState(pos).getBlock() != Blocks.REDSTONE_TORCH&& this.getBlockState(pos).getBlock() != Blocks.REDSTONE_BLOCK
					&& this.getBlockState(pos).getBlock() != Blocks.OBSERVER && !(this.getBlockState(pos).getBlock() instanceof ButtonBlock) && !(this.getBlockState(pos).getBlock() instanceof AbstractPressurePlateBlock))
			for (Direction upoffsetcheck : Direction.Type.HORIZONTAL) {
				if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() instanceof RedstoneWireBlock) {
					if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() != blockStateTest.getBlock()) {
						cir.setReturnValue(0);
					}
				} else if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() instanceof ColoredRedstoneWire) {
					cir.setReturnValue(0);
				}
			}

			if(!(this.getBlockState(pos).getBlock() instanceof AbstractRedstoneGateBlock) && this.getBlockState(pos).getBlock() != Blocks.LEVER&& this.getBlockState(pos).getBlock() != Blocks.REDSTONE_TORCH&& this.getBlockState(pos).getBlock() != Blocks.REDSTONE_BLOCK
					&& this.getBlockState(pos).getBlock() != Blocks.OBSERVER && !(this.getBlockState(pos).getBlock() instanceof ButtonBlock) && !(this.getBlockState(pos).getBlock() instanceof AbstractPressurePlateBlock))
			if (this.getBlockState(pos.offset(direction)).getBlock() instanceof RedstoneWireBlock) {
				if (this.getBlockState(pos.offset(direction)).getBlock() != blockStateTest.getBlock()) {
					cir.setReturnValue(0);
				}
			} else if (this.getBlockState(pos.offset(direction)).getBlock() instanceof ColoredRedstoneWire) {
				cir.setReturnValue(0);
			}
			if(direction!= Direction.UP){
			if (this.getBlockState(pos.up()).getBlock() instanceof RedstoneWireBlock) {
				if (this.getBlockState(pos.up()).getBlock() != blockStateTest.getBlock()) {
					cir.setReturnValue(0);
				}
			} else if (this.getBlockState(pos.up()).getBlock() instanceof ColoredRedstoneWire) {
				cir.setReturnValue(0);
			}
			}else{
				for (Direction upoffsetcheck : Direction.Type.HORIZONTAL) {
					if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() instanceof RedstoneWireBlock) {
						if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() != blockStateTest.getBlock()) {
							cir.setReturnValue(0);
						}
					} else if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() instanceof ColoredRedstoneWire) {
						cir.setReturnValue(0);
					}
				}
			}

			if(direction!= Direction.DOWN){
			if (this.getBlockState(pos.down()).getBlock() instanceof RedstoneWireBlock) {
				if (this.getBlockState(pos.down()).getBlock() != blockStateTest.getBlock()) {
					cir.setReturnValue(0);
				}
			} else if (this.getBlockState(pos.down()).getBlock() instanceof ColoredRedstoneWire) {
				cir.setReturnValue(0);
			}
		}else{
			for (Direction upoffsetcheck : Direction.Type.HORIZONTAL) {
				if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() instanceof RedstoneWireBlock) {
					if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() != blockStateTest.getBlock()) {
						cir.setReturnValue(0);
					}
				} else if (this.getBlockState(pos.offset(upoffsetcheck)).getBlock() instanceof ColoredRedstoneWire) {
					cir.setReturnValue(0);
				}
			}
		}
		}
	}


}
