package net.mcreator.marvelmashup.procedures;

@MarvelMashupModElements.ModElement.Tag
public class HulkEntityFallsProcedure extends MarvelMashupModElements.ModElement {

	public HulkEntityFallsProcedure(MarvelMashupModElements instance) {
		super(instance, 31);

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure HulkEntityFalls!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure HulkEntityFalls!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure HulkEntityFalls!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure HulkEntityFalls!");
			return;
		}

		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");

		world.destroyBlock(new BlockPos((int) x, (int) (y - 1), (int) z), false);

	}

}
