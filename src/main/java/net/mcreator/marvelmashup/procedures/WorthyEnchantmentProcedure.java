package net.mcreator.marvelmashup.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.EnchantmentHelper;

import net.mcreator.marvelmashup.enchantment.WorthyEnchantment;
import net.mcreator.marvelmashup.MarvelMashupModElements;

import java.util.Map;
import java.util.HashMap;

@MarvelMashupModElements.ModElement.Tag
public class WorthyEnchantmentProcedure extends MarvelMashupModElements.ModElement {
	public WorthyEnchantmentProcedure(MarvelMashupModElements instance) {
		super(instance, 32);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				System.err.println("Failed to load dependency entity for procedure WorthyEnchantment!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				System.err.println("Failed to load dependency x for procedure WorthyEnchantment!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				System.err.println("Failed to load dependency y for procedure WorthyEnchantment!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				System.err.println("Failed to load dependency z for procedure WorthyEnchantment!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				System.err.println("Failed to load dependency world for procedure WorthyEnchantment!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if (((EnchantmentHelper.getEnchantmentLevel(WorthyEnchantment.enchantment,
				((entity instanceof PlayerEntity) ? ((PlayerEntity) entity).inventory.armorInventory.get((int) 3) : ItemStack.EMPTY))) == 1)) {
			if (world instanceof World)
				world.getWorld().notifyNeighborsOfStateChange(new BlockPos((int) x, (int) (y - 1), (int) z),
						world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) z)).getBlock());
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Entity entity = event.player;
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
}
