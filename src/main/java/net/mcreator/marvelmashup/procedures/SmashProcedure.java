package net.mcreator.marvelmashup.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.block.Block;

import net.mcreator.marvelmashup.enchantment.HulkSmashEnchantment;
import net.mcreator.marvelmashup.MarvelMashupModElements;

import java.util.Map;
import java.util.HashMap;

@MarvelMashupModElements.ModElement.Tag
public class SmashProcedure extends MarvelMashupModElements.ModElement {
	public SmashProcedure(MarvelMashupModElements instance) {
		super(instance, 30);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure Smash!");
			return;
		}
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure Smash!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure Smash!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure Smash!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure Smash!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		boolean LogicVariable1 = false;
		double NumberVariable1 = 0;
		double NumberVariable2 = 0;
		double NumberVariable3 = 0;
		if (((EnchantmentHelper.getEnchantmentLevel(HulkSmashEnchantment.enchantment,
				((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY))) == 1)) {
			Block.spawnDrops(world.getBlockState(new BlockPos((int) (x - (-1)), (int) y, (int) z)), world.getWorld(),
					new BlockPos((int) (x - (-1)), (int) y, (int) z));
			world.destroyBlock(new BlockPos((int) (x - (-1)), (int) y, (int) z), false);
			Block.spawnDrops(world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) z)), world.getWorld(),
					new BlockPos((int) (x - 1), (int) y, (int) z));
			world.destroyBlock(new BlockPos((int) (x - 1), (int) y, (int) z), false);
			Block.spawnDrops(world.getBlockState(new BlockPos((int) x, (int) (y - (-1)), (int) z)), world.getWorld(),
					new BlockPos((int) x, (int) (y - (-1)), (int) z));
			world.destroyBlock(new BlockPos((int) x, (int) (y - (-1)), (int) z), false);
			Block.spawnDrops(world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) z)), world.getWorld(),
					new BlockPos((int) x, (int) (y - 1), (int) z));
			world.destroyBlock(new BlockPos((int) x, (int) (y - 1), (int) z), false);
			Block.spawnDrops(world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - (-1)))), world.getWorld(),
					new BlockPos((int) x, (int) y, (int) (z - (-1))));
			world.destroyBlock(new BlockPos((int) x, (int) y, (int) (z - (-1))), false);
			Block.spawnDrops(world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - 1))), world.getWorld(),
					new BlockPos((int) x, (int) y, (int) (z - 1)));
			world.destroyBlock(new BlockPos((int) x, (int) y, (int) (z - 1)), false);
		}
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		Entity entity = event.getPlayer();
		Map<String, Object> dependencies = new HashMap<>();
		dependencies.put("xpAmount", event.getExpToDrop());
		dependencies.put("x", (int) event.getPos().getX());
		dependencies.put("y", (int) event.getPos().getY());
		dependencies.put("z", (int) event.getPos().getZ());
		dependencies.put("px", entity.getPosX());
		dependencies.put("py", entity.getPosY());
		dependencies.put("pz", entity.getPosZ());
		dependencies.put("world", event.getWorld().getWorld());
		dependencies.put("entity", entity);
		dependencies.put("event", event);
		this.executeProcedure(dependencies);
	}
}
