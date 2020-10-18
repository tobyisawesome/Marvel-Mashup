
package net.mcreator.marvelmashup.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Rarity;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;

import net.mcreator.marvelmashup.MarvelMashupModElements;

@MarvelMashupModElements.ModElement.Tag
public class GravitronItem extends MarvelMashupModElements.ModElement {
	@ObjectHolder("marvel_mashup:gravitron")
	public static final Item block = null;
	public GravitronItem(MarvelMashupModElements instance) {
		super(instance, 22);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new MusicDiscItemCustom());
	}
	public static class MusicDiscItemCustom extends MusicDiscItem {
		public MusicDiscItemCustom() {
			super(0, MarvelMashupModElements.sounds.get(new ResourceLocation("marvel_mashup:gravitron")),
					new Item.Properties().group(ItemGroup.MISC).maxStackSize(1).rarity(Rarity.RARE));
			setRegistryName("gravitron");
		}
	}
}
