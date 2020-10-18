package net.mcreator.marvelmashup.procedures;

import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Rotation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Mirror;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.marvelmashup.MarvelMashupModVariables;
import net.mcreator.marvelmashup.MarvelMashupModElements;

import java.util.Map;
import java.util.HashMap;

@MarvelMashupModElements.ModElement.Tag
public class MlojnirSpawnProcedure extends MarvelMashupModElements.ModElement {
	public MlojnirSpawnProcedure(MarvelMashupModElements instance) {
		super(instance, 15);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure MlojnirSpawn!");
			return;
		}
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure MlojnirSpawn!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure MlojnirSpawn!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure MlojnirSpawn!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure MlojnirSpawn!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		double preferredx = 0;
		double preferredy = 0;
		double preferredz = 0;
		double adjust = 0;
		if ((4 < ((entity instanceof PlayerEntity) ? ((PlayerEntity) entity).experienceLevel : 0))) {
			if (((MarvelMashupModVariables.MapVariables.get(world).MjolnirLanded) == (false))) {
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
								|| (((world
										.getBlockState(new BlockPos((int) (preferredx), (int) (((preferredy) + (adjust)) - 5), (int) (preferredz))))
												.getMaterial() == net.minecraft.block.material.Material.ORGANIC)
										|| ((world.getBlockState(
												new BlockPos((int) (preferredx), (int) (((preferredy) + (adjust)) - 5), (int) (preferredz))))
														.getMaterial() == net.minecraft.block.material.Material.SAND))))
						|| ((world.getBlockState(new BlockPos((int) (preferredx), (int) (((preferredy) + (adjust)) - 5), (int) (preferredz))))
								.getMaterial() == net.minecraft.block.material.Material.PLANTS))
						&& (!(BlockTags.getCollection().getOrCreate(new ResourceLocation(("leaves").toLowerCase(java.util.Locale.ENGLISH))).contains(
								(world.getBlockState(new BlockPos((int) (preferredx), (int) (((preferredy) + (adjust)) - 5), (int) (preferredz))))
										.getBlock()))))) {
					if (!world.getWorld().isRemote) {
						Template template = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager()
								.getTemplateDefaulted(new ResourceLocation("marvel_mashup", "mlojnir"));
						if (template != null) {
							template.addBlocksToWorld(world,
									new BlockPos((int) (preferredx), (int) (((preferredy) + (adjust)) - 5), (int) (preferredz)),
									new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setChunk(null)
											.setIgnoreEntities(false));
						}
					}
					{
						MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
						if (mcserv != null)
							mcserv.getPlayerList().sendMessage(new StringTextComponent("Thor's hammer has landed!"));
					}
					MarvelMashupModVariables.MapVariables.get(world).MjolnirLanded = (boolean) (true);
					MarvelMashupModVariables.MapVariables.get(world).syncData(world);
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityEndSleep(PlayerWakeUpEvent event) {
		Entity entity = event.getEntity();
		World world = entity.world;
		double i = entity.getPosX();
		double j = entity.getPosY();
		double k = entity.getPosZ();
		Map<String, Object> dependencies = new HashMap<>();
		dependencies.put("x", i);
		dependencies.put("y", j);
		dependencies.put("z", k);
		dependencies.put("world", world);
		dependencies.put("entity", entity);
		dependencies.put("event", event);
		this.executeProcedure(dependencies);
	}
}
