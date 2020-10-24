
package net.mcreator.marvelmashup.enchantment;

@MarvelMashupModElements.ModElement.Tag
public class HulkSmashEnchantment extends MarvelMashupModElements.ModElement {

	@ObjectHolder("marvel_mashup:hulk_smash")
	public static final Enchantment enchantment = null;

	public HulkSmashEnchantment(MarvelMashupModElements instance) {
		super(instance, 28);
	}

	@Override
	public void initElements() {
		elements.enchantments.add(() -> new CustomEnchantment(EquipmentSlotType.MAINHAND).setRegistryName("hulk_smash"));
	}

	public static class CustomEnchantment extends Enchantment {

		public CustomEnchantment(EquipmentSlotType... slots) {
			super(Enchantment.Rarity.RARE, EnchantmentType.DIGGER, slots);
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
