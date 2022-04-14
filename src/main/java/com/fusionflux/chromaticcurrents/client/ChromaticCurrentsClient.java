package com.fusionflux.chromaticcurrents.client;

import net.fabricmc.api.ClientModInitializer;
import com.fusionflux.chromaticcurrents.ChromaticCurrents;
import com.fusionflux.chromaticcurrents.block.ColoredRedstoneWire;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Vec3d;

public class ChromaticCurrentsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.BLUESTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.GREENSTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.YELLOWSTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.ORANGESTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.PURPLESTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.LIGHTBLUESTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.LIMESTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.CYANSTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.WHITESTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.BLACKSTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.GRAYSTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.LIGHTGRAYSTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.MAGENTASTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.PINKSTONE_WIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChromaticCurrents.BROWNSTONE_WIRE, RenderLayer.getCutout());

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(0,0,1)), ChromaticCurrents.BLUESTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(0,.5,0)), ChromaticCurrents.GREENSTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(1,1,0)), ChromaticCurrents.YELLOWSTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(1,0.5,0)), ChromaticCurrents.ORANGESTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(.5,0,1)), ChromaticCurrents.PURPLESTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(1,1,1)), ChromaticCurrents.WHITESTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(0,0,0)), ChromaticCurrents.BLACKSTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(.75,.75,.75)), ChromaticCurrents.LIGHTGRAYSTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(.5,.5,.5)), ChromaticCurrents.GRAYSTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(.5,.25,0)), ChromaticCurrents.BROWNSTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(0,1,1)), ChromaticCurrents.LIGHTBLUESTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(0,.5,1)), ChromaticCurrents.CYANSTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(1,.5,1)), ChromaticCurrents.PINKSTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(1,0,1)), ChromaticCurrents.MAGENTASTONE_WIRE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ColoredRedstoneWire.getWireColorGood(state.get(Properties.POWER),new Vec3d(0,1,0)), ChromaticCurrents.LIMESTONE_WIRE);
    }
}
