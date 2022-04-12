package com.fusionflux.chromaticcurrents;

import net.fabricmc.api.ModInitializer;
import com.fusionflux.chromaticcurrents.block.ColoredRedstoneWire;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChromaticCurrents implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("chromatic_currents");

	public static final ItemGroup ChromaticCurrentsGroup = FabricItemGroupBuilder.create(
					new Identifier("chromatic_currents", "other"))
			.icon(() -> new ItemStack(ChromaticCurrents.ICONITEM))
			.build();

	public static final ColoredRedstoneWire BLUESTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly(),new Vec3d(0,0,1));
	public static final ColoredRedstoneWire GREENSTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.GREEN).noCollision().breakInstantly(),new Vec3d(0,.5,0));
	public static final ColoredRedstoneWire YELLOWSTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(1,1,0));
	public static final ColoredRedstoneWire ORANGESTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(1,0.5,0));
	public static final ColoredRedstoneWire PURPLESTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(.5,0,1));
	public static final ColoredRedstoneWire WHITESTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(1,1,1));
	public static final ColoredRedstoneWire BLACKSTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(0,0,0));
	public static final ColoredRedstoneWire BROWNSTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(.5,.25,0));
	public static final ColoredRedstoneWire MAGENTASTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(1,0,1));
	public static final ColoredRedstoneWire PINKSTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(1,0.5,1));
	public static final ColoredRedstoneWire LIMESTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(0,1,0));
	public static final ColoredRedstoneWire CYANSTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(0,.5,1));
	public static final ColoredRedstoneWire LIGHTBLUESTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(0,1,1));
	public static final ColoredRedstoneWire GRAYSTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(.5,.5,.5));
	public static final ColoredRedstoneWire LIGHTGRAYSTONE_WIRE = new ColoredRedstoneWire(FabricBlockSettings.of(Material.DECORATION, DyeColor.WHITE).noCollision().breakInstantly(),new Vec3d(.75,.75,.75));
	public static final Item ICONITEM = new Item(new FabricItemSettings());



	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "whitestone_wire"), WHITESTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "whitestone"), new BlockItem(WHITESTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "orangestone_wire"), ORANGESTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "orangestone"), new BlockItem(ORANGESTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "magentastone_wire"), MAGENTASTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "magentastone"), new BlockItem(MAGENTASTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "lightbluestone_wire"), LIGHTBLUESTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "lightbluestone"), new BlockItem(LIGHTBLUESTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "yellowstone_wire"), YELLOWSTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "yellowstone"), new BlockItem(YELLOWSTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "limestone_wire"), LIMESTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "limestone"), new BlockItem(LIMESTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "pinkstone_wire"), PINKSTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "pinkstone"), new BlockItem(PINKSTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "graystone_wire"), GRAYSTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "graystone"), new BlockItem(GRAYSTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "lightgraystone_wire"), LIGHTGRAYSTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "lightgraystone"), new BlockItem(LIGHTGRAYSTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "cyanstone_wire"), CYANSTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "cyanstone"), new BlockItem(CYANSTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "purplestone_wire"), PURPLESTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "purplestone"), new BlockItem(PURPLESTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "bluestone_wire"), BLUESTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "bluestone"), new BlockItem(BLUESTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "brownstone_wire"), BROWNSTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "brownstone"), new BlockItem(BROWNSTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "greenstone_wire"), GREENSTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "greenstone"), new BlockItem(GREENSTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.BLOCK, new Identifier("chromatic_currents", "blackstone_wire"), BLACKSTONE_WIRE);
		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "blackstone"), new BlockItem(BLACKSTONE_WIRE, new Item.Settings().group(ChromaticCurrents.ChromaticCurrentsGroup)));

		Registry.register(Registry.ITEM, new Identifier("chromatic_currents", "iconitem"), ICONITEM);


		LOGGER.info("Hello Fabric world!");
	}
}
