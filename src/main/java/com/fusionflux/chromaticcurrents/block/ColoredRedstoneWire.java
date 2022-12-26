package com.fusionflux.chromaticcurrents.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.block.*;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ColoredRedstoneWire extends Block {
    public static final EnumProperty<WireConnection> WIRE_CONNECTION_NORTH;
    public static final EnumProperty<WireConnection> WIRE_CONNECTION_EAST;
    public static final EnumProperty<WireConnection> WIRE_CONNECTION_SOUTH;
    public static final EnumProperty<WireConnection> WIRE_CONNECTION_WEST;
    public static final IntProperty POWER;
    public static final Map<Direction, EnumProperty<WireConnection>> DIRECTION_TO_WIRE_CONNECTION_PROPERTY;
    private static final VoxelShape DOT_SHAPE;
    private static final Map<Direction, VoxelShape> field_24414;
    private static final Map<Direction, VoxelShape> field_24415;
    private static final Map<BlockState, VoxelShape> SHAPES;
    public static final Vec3d[] COLORS;
    public final Vec3d COLORMODIFYER;
    private final BlockState dotState;
    private boolean wiresGivePower = true;

    public ColoredRedstoneWire(Settings settings,Vec3d color) {
        super(settings);
        this.setDefaultState((((((this.stateManager.getDefaultState()).with(WIRE_CONNECTION_NORTH, WireConnection.NONE)).with(WIRE_CONNECTION_EAST, WireConnection.NONE)).with(WIRE_CONNECTION_SOUTH, WireConnection.NONE)).with(WIRE_CONNECTION_WEST, WireConnection.NONE)).with(POWER, 0));
        this.dotState = (((this.getDefaultState().with(WIRE_CONNECTION_NORTH, WireConnection.SIDE)).with(WIRE_CONNECTION_EAST, WireConnection.SIDE)).with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE)).with(WIRE_CONNECTION_WEST, WireConnection.SIDE);
        UnmodifiableIterator<BlockState> var2 = this.getStateManager().getStates().iterator();
        COLORMODIFYER = color;
        while(var2.hasNext()) {
            BlockState blockState = var2.next();
            if (blockState.get(POWER) == 0) {
                SHAPES.put(blockState, this.getShapeForState(blockState));
            }
        }

    }

    private VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelShape = DOT_SHAPE;

        for (Direction direction : Direction.Type.HORIZONTAL) {
            WireConnection wireConnection = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            if (wireConnection == WireConnection.SIDE) {
                voxelShape = VoxelShapes.union(voxelShape, field_24414.get(direction));
            } else if (wireConnection == WireConnection.UP) {
                voxelShape = VoxelShapes.union(voxelShape, field_24415.get(direction));
            }
        }

        return voxelShape;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.with(POWER, 0));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getPlacementState(ctx.getWorld(), this.dotState, ctx.getBlockPos());
    }

    private BlockState getPlacementState(BlockView world, BlockState state, BlockPos pos) {
        boolean bl = isNotConnected(state);
        state = this.getDefaultWireState(world, this.getDefaultState().with(POWER, state.get(POWER)), pos);
        if (!bl || !isNotConnected(state)) {
            boolean bl2 = (state.get(WIRE_CONNECTION_NORTH)).isConnected();
            boolean bl3 = (state.get(WIRE_CONNECTION_SOUTH)).isConnected();
            boolean bl4 = (state.get(WIRE_CONNECTION_EAST)).isConnected();
            boolean bl5 = (state.get(WIRE_CONNECTION_WEST)).isConnected();
            boolean bl6 = !bl2 && !bl3;
            boolean bl7 = !bl4 && !bl5;
            if (!bl5 && bl6) {
                state = state.with(WIRE_CONNECTION_WEST, WireConnection.SIDE);
            }

            if (!bl4 && bl6) {
                state = state.with(WIRE_CONNECTION_EAST, WireConnection.SIDE);
            }

            if (!bl2 && bl7) {
                state = state.with(WIRE_CONNECTION_NORTH, WireConnection.SIDE);
            }

            if (!bl3 && bl7) {
                state = state.with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE);
            }

        }
        return state;
    }

    private BlockState getDefaultWireState(BlockView world, BlockState state, BlockPos pos) {
        boolean bl = !world.getBlockState(pos.up()).isSolidBlock(world, pos);

        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (!(state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected()) {
                WireConnection wireConnection = this.getRenderConnectionType(world, pos, direction, bl, state);
                state = state.with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection);
            }
        }

        return state;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            return state;
        } else if (direction == Direction.UP) {
            return this.getPlacementState(world, state, pos);
        } else {
            WireConnection wireConnection = this.getRenderConnectionType(world, pos, direction,state);
            return wireConnection.isConnected() == (state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected() && !isFullyConnected(state) ? state.with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection) : this.getPlacementState(world, (this.dotState.with(POWER, state.get(POWER))).with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection), pos);
        }
    }

    private static boolean isFullyConnected(BlockState state) {
        return (state.get(WIRE_CONNECTION_NORTH)).isConnected() && (state.get(WIRE_CONNECTION_SOUTH)).isConnected() && (state.get(WIRE_CONNECTION_EAST)).isConnected() && (state.get(WIRE_CONNECTION_WEST)).isConnected();
    }

    private static boolean isNotConnected(BlockState state) {
        return !(state.get(WIRE_CONNECTION_NORTH)).isConnected() && !(state.get(WIRE_CONNECTION_SOUTH)).isConnected() && !(state.get(WIRE_CONNECTION_EAST)).isConnected() && !(state.get(WIRE_CONNECTION_WEST)).isConnected();
    }

    public void prepare(BlockState state, WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (Direction direction : Direction.Type.HORIZONTAL) {
            WireConnection wireConnection =  state.get( DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            if (wireConnection != WireConnection.NONE && !world.getBlockState(mutable.set(pos, direction)).isOf(this)) {
                mutable.move(Direction.DOWN);
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isOf(Blocks.OBSERVER)) {
                    BlockPos blockPos = mutable.offset(direction.getOpposite());
                   // System.out.println(blockState + " Prepare DOWN 1");
                    BlockState blockState2 = blockState.getStateForNeighborUpdate(direction.getOpposite(), world.getBlockState(blockPos), world, mutable, blockPos);
                   // System.out.println(blockState2 + "Prepare DOWN 2");
                    replace(blockState, blockState2, world, mutable, flags, maxUpdateDepth);
                }

                mutable.set(pos, direction).move(Direction.UP);
                BlockState blockState3 = world.getBlockState(mutable);
                if (!blockState3.isOf(Blocks.OBSERVER)) {
                    BlockPos blockPos2 = mutable.offset(direction.getOpposite());
                   // System.out.println(blockState3 + "Prepare UP 1");
                    BlockState blockState4 = blockState3.getStateForNeighborUpdate(direction.getOpposite(), world.getBlockState(blockPos2), world, mutable, blockPos2);
                   // System.out.println(blockState4 + "Prepare UP 2");
                    replace(blockState3, blockState4, world, mutable, flags, maxUpdateDepth);
                }
            }
        }

    }

    private WireConnection getRenderConnectionType(BlockView world, BlockPos pos, Direction direction,BlockState originalState) {
        return this.getRenderConnectionType(world, pos, direction, !world.getBlockState(pos.up()).isSolidBlock(world, pos),originalState);
    }

    private WireConnection getRenderConnectionType(BlockView world, BlockPos pos, Direction direction, boolean bl,BlockState originalState) {
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (bl) {
            boolean bl2 = this.canRunOnTop(world, blockPos, blockState);
            if (bl2 && connectsTo(world.getBlockState(blockPos.up()),originalState)) {
                if (blockState.isSideSolidFullSquare(world, blockPos, direction.getOpposite())) {
                    return WireConnection.UP;
                }

                return WireConnection.SIDE;
            }
        }

        return !connectsTo(blockState, originalState,direction) && (blockState.isSolidBlock(world, blockPos) || !connectsTo(world.getBlockState(blockPos.down()),originalState)) ? WireConnection.NONE : WireConnection.SIDE;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        return this.canRunOnTop(world, blockPos, blockState);
    }

    private boolean canRunOnTop(BlockView world, BlockPos pos, BlockState floor) {
        return floor.isSideSolidFullSquare(world, pos, Direction.UP) || floor.isOf(Blocks.HOPPER);
    }

    private void update(World world, BlockPos pos, BlockState state) {
        int i = this.getReceivedRedstonePower(world, pos);
        //System.out.println("UPDATEHIT");
        //System.out.println("State "+ state);
        //System.out.println("Power "+ i);
        if (state.get(POWER) != i) {
            if (world.getBlockState(pos) == state) {
                world.setBlockState(pos, state.with(POWER, i), 2);
            }

            Set<BlockPos> set = Sets.newHashSet();
            set.add(pos);
            Direction[] var6 = Direction.values();

            for (Direction direction : var6) {
                set.add(pos.offset(direction));
            }

            for (BlockPos blockPos : set) {
                world.updateNeighborsAlways(blockPos, this);
            }
        }

    }



    private int getReceivedRedstonePower(World world, BlockPos pos) {
        this.wiresGivePower = false;
        int i = world.getReceivedRedstonePower(pos);

        this.wiresGivePower = true;
        int j = 0;
        if (i < 15) {


            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                //System.out.println(blockState + "from get recivedredstone");
                j = Math.max(j, this.increasePower(blockState));
                BlockPos blockPos2 = pos.up();
                if (blockState.isSolidBlock(world, blockPos) && !world.getBlockState(blockPos2).isSolidBlock(world, blockPos2)) {
                    //System.out.println(world.getBlockState(blockPos.up()) + "from get recivedredstone up");
                    j = Math.max(j, this.increasePower(world.getBlockState(blockPos.up())));
                } else if (!blockState.isSolidBlock(world, blockPos)) {
                    //System.out.println(world.getBlockState(blockPos.down()) + "from get recivedredstone down");
                    j = Math.max(j, this.increasePower(world.getBlockState(blockPos.down())));
                }
            }

                return Math.max(i, j - 1);

        } else {
            return i;
        }
    }

    private int increasePower(BlockState state) {

        return state.isOf(this) ? state.get(POWER) : 0;
    }

    private void updateNeighbors(World world, BlockPos pos) {
        if (world.getBlockState(pos).isOf(this)) {
            world.updateNeighborsAlways(pos, this);
            Direction[] var3 = Direction.values();

            for (Direction direction : var3) {
                world.updateNeighborsAlways(pos.offset(direction), this);
            }

        }
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock()) && !world.isClient) {
            this.update(world, pos, state);

            for (Direction direction : Direction.Type.VERTICAL) {
                world.updateNeighborsAlways(pos.offset(direction), this);
            }

            this.updateOffsetNeighbors(world, pos);
        }
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!moved && !state.isOf(newState.getBlock())) {
            super.onStateReplaced(state, world, pos, newState, false);
            if (!world.isClient) {
                Direction[] var6 = Direction.values();

                for (Direction direction : var6) {
                    world.updateNeighborsAlways(pos.offset(direction), this);
                }

                this.update(world, pos, state);
                this.updateOffsetNeighbors(world, pos);
            }
        }
    }

    private void updateOffsetNeighbors(World world, BlockPos pos) {
        Iterator<Direction> var3 = Direction.Type.HORIZONTAL.iterator();

        Direction direction;
        while(var3.hasNext()) {
            direction = var3.next();
            this.updateNeighbors(world, pos.offset(direction));
            //System.out.println(world.getBlockState(pos.offset(direction)) + "UpdateOffsetNeighbors1");
        }

        var3 = Direction.Type.HORIZONTAL.iterator();

        while(var3.hasNext()) {
            direction = var3.next();
            BlockPos blockPos = pos.offset(direction);
            if (world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
                this.updateNeighbors(world, blockPos.up());
                //System.out.println(world.getBlockState(blockPos.up()) + "UpdateOffsetNeighborsup");
            } else {
                this.updateNeighbors(world, blockPos.down());
                //System.out.println(world.getBlockState(blockPos.down()) + "UpdateOffsetNeighborsdown");
            }
        }

    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            if (state.canPlaceAt(world, pos)) {
                this.update(world, pos, state);
            } else {
                dropStacks(state, world, pos);
                world.removeBlock(pos, false);
            }

        }
    }

    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        //System.out.println("GETSTRONGPOWERHIT");
        return !this.wiresGivePower ? 0 : state.getWeakRedstonePower(world, pos, direction);
    }

    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {

        if (this.wiresGivePower && direction != Direction.DOWN) {
            int i = state.get(POWER);
            if (i == 0) {
                return 0;
            } else {
                return direction != Direction.UP && !(this.getPlacementState(world, state, pos).get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction.getOpposite()))).isConnected() ? 0 : i;
            }
        } else {
            return 0;
        }
    }

    protected static boolean connectsTo(BlockState state,BlockState originalState) {
        return connectsTo(state,originalState, null);
    }

    protected static boolean connectsTo(BlockState state,BlockState originalState, @Nullable Direction dir) {
        if (state.getBlock() == originalState.getBlock()) {
            return true;
        }else if (state.isOf(Blocks.REDSTONE_WIRE)) {
            return false;
        } else if (state.isOf(Blocks.REPEATER)) {
            Direction direction = state.get(RepeaterBlock.FACING);
            return direction == dir || direction.getOpposite() == dir;
        } else if (state.isOf(Blocks.OBSERVER)) {
            return dir == state.get(ObserverBlock.FACING);
        } else {
            if(state.getBlock() instanceof ColoredRedstoneWire){
                if(state.getBlock() != originalState.getBlock()){
                    return false;
                }
            }
            return state.emitsRedstonePower() && dir != null;
        }
    }

    public boolean emitsRedstonePower(BlockState state) {
        return this.wiresGivePower;
    }

    public static int getWireColorGood(int powerLevel,Vec3d startColor) {
        Vec3d vec3d = COLORS[powerLevel];
        return MathHelper.packRgb((float)(vec3d.getX()*startColor.getX()), (float)(vec3d.getX()*startColor.getY()), (float)(vec3d.getX()*startColor.getZ()));
    }


    private void addPoweredParticles(World world, Random random, BlockPos pos, Vec3d color, Direction direction, Direction direction2, float f, float g) {
        float h = g - f;
        if (!(random.nextFloat() >= 0.2F * h)) {
            float j = f + h * random.nextFloat();
            double d = 0.5D + (double)(0.4375F * (float)direction.getOffsetX()) + (double)(j * (float)direction2.getOffsetX());
            double e = 0.5D + (double)(0.4375F * (float)direction.getOffsetY()) + (double)(j * (float)direction2.getOffsetY());
            double k = 0.5D + (double)(0.4375F * (float)direction.getOffsetZ()) + (double)(j * (float)direction2.getOffsetZ());
            world.addParticle(new DustParticleEffect(new Vector3f((float) color.getX(), (float) color.getY(),(float) color.getZ()), 1.0F), (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + k, 0.0D, 0.0D, 0.0D);
        }
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = state.get(POWER);
        if (i != 0) {

            for (Direction direction : Direction.Type.HORIZONTAL) {
                WireConnection wireConnection = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
                switch (wireConnection) {
                    case UP:
                        this.addPoweredParticles(world, random, pos, new Vec3d(COLORS[i].getX() * COLORMODIFYER.getX(), COLORS[i].getX() * COLORMODIFYER.getY(), COLORS[i].getX() * COLORMODIFYER.getZ()), direction, Direction.UP, -0.5F, 0.5F);
                    case SIDE:
                        this.addPoweredParticles(world, random, pos, new Vec3d(COLORS[i].getX() * COLORMODIFYER.getX(), COLORS[i].getX() * COLORMODIFYER.getY(), COLORS[i].getX() * COLORMODIFYER.getZ()), Direction.DOWN, direction, 0.0F, 0.5F);
                        break;
                    case NONE:
                    default:
                        this.addPoweredParticles(world, random, pos, new Vec3d(COLORS[i].getX() * COLORMODIFYER.getX(), COLORS[i].getX() * COLORMODIFYER.getY(), COLORS[i].getX() * COLORMODIFYER.getZ()), Direction.DOWN, direction, 0.0F, 0.3F);
                }
            }

        }
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 ->  ( ( ( state.with(WIRE_CONNECTION_NORTH,  state.get(WIRE_CONNECTION_SOUTH))).with(WIRE_CONNECTION_EAST,  state.get(WIRE_CONNECTION_WEST))).with(WIRE_CONNECTION_SOUTH,  state.get(WIRE_CONNECTION_NORTH))).with(WIRE_CONNECTION_WEST,  state.get(WIRE_CONNECTION_EAST));
            case COUNTERCLOCKWISE_90 ->  ( ( ( state.with(WIRE_CONNECTION_NORTH,  state.get(WIRE_CONNECTION_EAST))).with(WIRE_CONNECTION_EAST,  state.get(WIRE_CONNECTION_SOUTH))).with(WIRE_CONNECTION_SOUTH,  state.get(WIRE_CONNECTION_WEST))).with(WIRE_CONNECTION_WEST,  state.get(WIRE_CONNECTION_NORTH));
            case CLOCKWISE_90 ->  ( ( ( state.with(WIRE_CONNECTION_NORTH,  state.get(WIRE_CONNECTION_WEST))).with(WIRE_CONNECTION_EAST,  state.get(WIRE_CONNECTION_NORTH))).with(WIRE_CONNECTION_SOUTH,  state.get(WIRE_CONNECTION_EAST))).with(WIRE_CONNECTION_WEST,  state.get(WIRE_CONNECTION_SOUTH));
            default -> state;
        };
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> (state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_SOUTH))).with(WIRE_CONNECTION_SOUTH,  state.get(WIRE_CONNECTION_NORTH));
            case FRONT_BACK ->  ( state.with(WIRE_CONNECTION_EAST,  state.get(WIRE_CONNECTION_WEST))).with(WIRE_CONNECTION_WEST,  state.get(WIRE_CONNECTION_EAST));
            default -> super.mirror(state, mirror);
        };
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WIRE_CONNECTION_NORTH, WIRE_CONNECTION_EAST, WIRE_CONNECTION_SOUTH, WIRE_CONNECTION_WEST, POWER);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getAbilities().allowModifyWorld) {
            if (isFullyConnected(state) || isNotConnected(state)) {
                BlockState blockState = isFullyConnected(state) ? this.getDefaultState() : this.dotState;
                blockState = blockState.with(POWER, state.get(POWER));
                blockState = this.getPlacementState(world, blockState, pos);
                if (blockState != state) {
                    world.setBlockState(pos, blockState, 3);
                    this.updateForNewState(world, pos, state, blockState);
                    return ActionResult.SUCCESS;
                }
            }

        }
        return ActionResult.PASS;
    }

    private void updateForNewState(World world, BlockPos pos, BlockState oldState, BlockState newState) {

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = pos.offset(direction);
            if (( oldState.get( DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected() != ( newState.get( DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected() && world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
                world.updateNeighborsExcept(blockPos, newState.getBlock(), direction.getOpposite());
            }
        }

    }

    static {
        WIRE_CONNECTION_NORTH = Properties.NORTH_WIRE_CONNECTION;
        WIRE_CONNECTION_EAST = Properties.EAST_WIRE_CONNECTION;
        WIRE_CONNECTION_SOUTH = Properties.SOUTH_WIRE_CONNECTION;
        WIRE_CONNECTION_WEST = Properties.WEST_WIRE_CONNECTION;
        POWER = Properties.POWER;
        DIRECTION_TO_WIRE_CONNECTION_PROPERTY = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, WIRE_CONNECTION_NORTH, Direction.EAST, WIRE_CONNECTION_EAST, Direction.SOUTH, WIRE_CONNECTION_SOUTH, Direction.WEST, WIRE_CONNECTION_WEST));
        DOT_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
        field_24414 = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.createCuboidShape(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Direction.SOUTH, Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Direction.EAST, Block.createCuboidShape(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Direction.WEST, Block.createCuboidShape(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));
        field_24415 = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, VoxelShapes.union(field_24414.get(Direction.NORTH), Block.createCuboidShape(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)), Direction.SOUTH, VoxelShapes.union(field_24414.get(Direction.SOUTH), Block.createCuboidShape(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)), Direction.EAST, VoxelShapes.union(field_24414.get(Direction.EAST), Block.createCuboidShape(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)), Direction.WEST, VoxelShapes.union(field_24414.get(Direction.WEST), Block.createCuboidShape(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))));
        SHAPES = Maps.newHashMap();


        COLORS = Util.make(new Vec3d[16], (vec3ds) -> {
            for(int i = 0; i <= 15; ++i) {
                float f = (float)i / 15.0F;
                float g = f * 0.6F + (f > 0.0F ? 0.4F : 0.3F);
                float h = MathHelper.clamp(f * f * 0.7F - 0.5F, 0.0F, 1.0F);
                float j = MathHelper.clamp(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
                vec3ds[i] = new Vec3d(g, h, j);
            }

        });
    }
}