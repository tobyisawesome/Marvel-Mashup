package net.mcreator.marvelmashup.procedures;

import net.minecraft.entity.Entity;

import net.mcreator.marvelmashup.MarvelMashupModElements;

import java.util.Map;

@MarvelMashupModElements.ModElement.Tag
public class MeteoriteEntityWalksOnTheBlockProcedure extends MarvelMashupModElements.ModElement {
	public MeteoriteEntityWalksOnTheBlockProcedure(MarvelMashupModElements instance) {
		super(instance, 36);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				System.err.println("Failed to load dependency entity for procedure MeteoriteEntityWalksOnTheBlock!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		entity.setFire((int) 3);
	}
}
