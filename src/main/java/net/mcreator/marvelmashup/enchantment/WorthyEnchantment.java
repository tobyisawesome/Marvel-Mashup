
package net.mcreator.marvelmashup.enchantment;

@MarvelMashupModElements.ModElement.Tag
public class WorthyEnchantment extends MarvelMashupModElements.ModElement {

	@ObjectHolder("marvel_mashup:time_shift")
	public static final Enchantment enchantment = null;

	public WorthyEnchantment(MarvelMashupModElements instance) {
		super(instance, 29);
	}

	@Override
	public void initElements() {
		elements.enchantments.add(() -> new CustomEnchantment(EquipmentSlotType.MAINHAND).setRegistryName("time_shift"));
	}

	public static class CustomEnchantment extends Enchantment {

		public CustomEnchantment(EquipmentSlotType... slots) {
			super(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_FEET, slots);
		}

		@Override
		public int getMinLevel() {
			return 1;
		}

		@Override
		public int getMaxLevel() {
			return 1;
		}

		@Override
		public boolean isTreasureEnchantment() {
			return false;
		}

		@Override
		public boolean isCurse() {
			return false;
		}

		@Override
		public boolean isAllowedOnBooks() {
			return true;
		}

	}

}
