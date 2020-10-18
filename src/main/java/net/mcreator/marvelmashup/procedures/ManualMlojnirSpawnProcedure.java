package net.mcreator.marvelmashup.procedures;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Rotation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Mirror;
import net.minecraft.tags.BlockTags;

import net.mcreator.marvelmashup.MarvelMashupModElements;

import java.util.Map;

@MarvelMashupModElements.ModElement.Tag
public class ManualMlojnirSpawnProcedure extends MarvelMashupModElements.ModElement {
	public ManualMlojnirSpawnProcedure(MarvelMashupModElements instance) {
		super(instance, 16);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure ManualMlojnirSpawn!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure ManualMlojnirSpawn!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure ManualMlojnirSpawn!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure ManualMlojnirSpawn!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		double adjust = 0;
		double preferredx = 0;
		double preferredy = 0;
		double preferredz = 0;
		preferredx = (double) (x + ((Math.random() * 100) + (Math.random() * (-100))));
		preferredz = (double) (z + ((Math.random() * 100) + (Math.random() * (-100))));
		preferredy = (double) y;
		adjust = (double) 0;
		if (((world.isAirBlock(new BlockPos((int) (preferredx), (int) (preferredy), (int) (preferredz))))
				&& (world.canBlockSeeSky(new BlockPos((int) (preferredx), (int) (preferredy), (int) (preferredz)))))) {
			while (((world.isAirBlock(new BlockPos((int) (preferredx), (int) ((preferredy) + (adjust)), (int) (preferredz))))
					&& (world.canBlockSeeSky(new BlockPos((int) (preferredx), (int) ((preferredy) + (adjust)), (int) (preferredz)))))) {
				adjust = (double) ((adjust) - 1);
			}
		}
		if ((!(world.canBlockSeeSky(new BlockPos((int) (preferredx), (int) (preferredy), (int) (preferredz)))))) {
			while (((world.isAirBlock(new BlockPos((int) (preferredx), (int) ((preferredy) + (adjust)), (int) (preferredz))))
					&& (!(world.canBlockSeeSky(new BlockPos((int) (preferredx), (int) ((preferredy) + (adjust)), (int) (preferredz))))))) {
				adjust = (double) ((adjust) + 1);
			}
		}
		if ((((((world.getBlockState(new BlockPos((int) (preferredx), (int) (((preferredy) + (adjust)) - 5), (int) (preferredz))))
				.getMaterial() == net.minecraft.block.material.Material.ROCK)
				|| (((world.getBlockState(new BlockPos((int) (preferredx), (int) (((preferredy) + (adjust)) - 5), (int) (preferredz))))
						.getMaterial() == net.minecraft.block.material.Material.EARTH)
						|| (((world.getBlockState(new BlockPos((int) (preferredx), (int) (((preferredy) + (adjust)) - 5), (int) (preferredz))))
								.getMaterial() == net.minecraft.block.material.Material.ORGANIC)
								|| ((world.getBlockState(new BlockPos((int) (preferredx), (int) (((preferredy) + (adjust)) - 5), (int) (preferredz))))
										.getMaterial() == net.minecraft.block.material.Material.SAND))))
				|| ((world.getBlockState(new BlockPos((int) (preferredx), (int) (((preferredy) + (adjust)) - 5), (int) (preferredz))))
						.getMaterial() == net.minecraft.block.material.Material.PLANTS))
				&& (!(BlockTags.getCollection().getOrCreate(new ResourceLocation(("leaves").toLowerCase(java.util.Locale.ENGLISH)))
						.contains((world.getBlockState(new BlockPos((int) (preferredx), (int) (((preferredy) + (adjust)) - 5), (int) (preferredz))))
								.getBlock()))))) {
			if (!world.getWorld().isRemote) {
				Template template = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager()
						.getTemplateDefaulted(new ResourceLocation("marvel_mashup", "mlojnir"));
				if (template != null) {
					template.addBlocksToWorld(world, new BlockPos((int) (preferredx), (int) (((preferredy) + (adjust)) - 5), (int) (preferredz)),
							new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setChunk(null).setIgnoreEntities(false));
				}
			}
		}
	}
}
