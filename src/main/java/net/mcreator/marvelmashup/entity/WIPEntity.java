
package net.mcreator.marvelmashup.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.block.BlockState;

import net.mcreator.marvelmashup.MarvelMashupModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@MarvelMashupModElements.ModElement.Tag
public class WIPEntity extends MarvelMashupModElements.ModElement {
	public static EntityType entity = null;
	public WIPEntity(MarvelMashupModElements instance) {
		super(instance, 27);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 1.8f)).build("wip")
						.setRegistryName("wip");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("wip"));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
			biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(entity, 20, 4, 4));
		}
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				MonsterEntity::canMonsterSpawn);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
			return new MobRenderer(renderManager, new ModelNew(), 0.5f) {
				@Override
				public ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("marvel_mashup:textures/wolf-planetminecraft-com-13499114.png");
				}
			};
		});
	}
	public static class CustomEntity extends MonsterEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 0;
			setNoAI(false);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
			this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 1));
			this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
			this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(5, new SwimGoal(this));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wolf.ambient"));
		}

		@Override
		public void playStepSound(BlockPos pos, BlockState blockIn) {
			this.playSound((net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wolf.step")), 0.15f, 1);
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wolf.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wolf.death"));
		}

		@Override
		protected void registerAttributes() {
			super.registerAttributes();
			if (this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
			if (this.getAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
			if (this.getAttribute(SharedMonsterAttributes.ARMOR) != null)
				this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0);
			if (this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null)
				this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
			this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3);
		}
	}

	// Date: 07.12.2018 16:35:12
	// Template version 1.1
	// Java generated by Techne
	// Keep in mind that you still need to fill in some blanks
	// - ZeuX
	public static class ModelNew extends EntityModel<Entity> {
		// fields
		ModelRenderer WolfHead;
		ModelRenderer Body;
		ModelRenderer Mane;
		ModelRenderer Leg1;
		ModelRenderer Leg2;
		ModelRenderer Leg3;
		ModelRenderer Leg4;
		ModelRenderer Tail;
		ModelRenderer Ear1;
		ModelRenderer Ear2;
		ModelRenderer Nose;
		public ModelNew() {
			textureWidth = 64;
			textureHeight = 32;
			WolfHead = new ModelRenderer(this, 0, 0);
			WolfHead.addBox(-3F, -3F, -2F, 6, 6, 4);
			WolfHead.setRotationPoint(-1F, 13.5F, -7F);
			WolfHead.setTextureSize(64, 32);
			WolfHead.mirror = true;
			setRotation(WolfHead, 0F, 0F, 0F);
			Body = new ModelRenderer(this, 18, 14);
			Body.addBox(-4F, -2F, -3F, 6, 9, 6);
			Body.setRotationPoint(0F, 14F, 2F);
			Body.setTextureSize(64, 32);
			Body.mirror = true;
			setRotation(Body, 1.570796F, 0F, 0F);
			Mane = new ModelRenderer(this, 21, 0);
			Mane.addBox(-4F, -3F, -3F, 8, 6, 7);
			Mane.setRotationPoint(-1F, 14F, -3F);
			Mane.setTextureSize(64, 32);
			Mane.mirror = true;
			setRotation(Mane, 1.570796F, 0F, 0F);
			Leg1 = new ModelRenderer(this, 0, 18);
			Leg1.addBox(-1F, 0F, -1F, 2, 8, 2);
			Leg1.setRotationPoint(-2.5F, 16F, 7F);
			Leg1.setTextureSize(64, 32);
			Leg1.mirror = true;
			setRotation(Leg1, 0F, 0F, 0F);
			Leg2 = new ModelRenderer(this, 0, 18);
			Leg2.addBox(-1F, 0F, -1F, 2, 8, 2);
			Leg2.setRotationPoint(0.5F, 16F, 7F);
			Leg2.setTextureSize(64, 32);
			Leg2.mirror = true;
			setRotation(Leg2, 0F, 0F, 0F);
			Leg3 = new ModelRenderer(this, 0, 18);
			Leg3.addBox(-1F, 0F, -1F, 2, 8, 2);
			Leg3.setRotationPoint(-2.5F, 16F, -4F);
			Leg3.setTextureSize(64, 32);
			Leg3.mirror = true;
			setRotation(Leg3, 0F, 0F, 0F);
			Leg4 = new ModelRenderer(this, 0, 18);
			Leg4.addBox(-1F, 0F, -1F, 2, 8, 2);
			Leg4.setRotationPoint(0.5F, 16F, -4F);
			Leg4.setTextureSize(64, 32);
			Leg4.mirror = true;
			setRotation(Leg4, 0F, 0F, 0F);
			Tail = new ModelRenderer(this, 9, 18);
			Tail.addBox(-1F, 0F, -1F, 2, 8, 2);
			Tail.setRotationPoint(-1F, 12F, 8F);
			Tail.setTextureSize(64, 32);
			Tail.mirror = true;
			setRotation(Tail, 1.130069F, 0F, 0F);
			Ear1 = new ModelRenderer(this, 16, 14);
			Ear1.addBox(-3F, -5F, 0F, 2, 2, 1);
			Ear1.setRotationPoint(-1F, 13.5F, -7F);
			Ear1.setTextureSize(64, 32);
			Ear1.mirror = true;
			setRotation(Ear1, 0F, 0F, 0F);
			Ear2 = new ModelRenderer(this, 16, 14);
			Ear2.addBox(1F, -5F, 0F, 2, 2, 1);
			Ear2.setRotationPoint(-1F, 13.5F, -7F);
			Ear2.setTextureSize(64, 32);
			Ear2.mirror = true;
			setRotation(Ear2, 0F, 0F, 0F);
			Nose = new ModelRenderer(this, 0, 10);
			Nose.addBox(-2F, 0F, -5F, 3, 3, 4);
			Nose.setRotationPoint(-0.5F, 13.5F, -7F);
			Nose.setTextureSize(64, 32);
			Nose.mirror = true;
			setRotation(Nose, 0F, 0F, 0F);
		}

		public void render(MatrixStack ms, IVertexBuilder vb, int i1, int i2, float f1, float f2, float f3, float f4) {
			WolfHead.render(ms, vb, i1, i2, f1, f2, f3, f4);
			Body.render(ms, vb, i1, i2, f1, f2, f3, f4);
			Mane.render(ms, vb, i1, i2, f1, f2, f3, f4);
			Leg1.render(ms, vb, i1, i2, f1, f2, f3, f4);
			Leg2.render(ms, vb, i1, i2, f1, f2, f3, f4);
			Leg3.render(ms, vb, i1, i2, f1, f2, f3, f4);
			Leg4.render(ms, vb, i1, i2, f1, f2, f3, f4);
			Tail.render(ms, vb, i1, i2, f1, f2, f3, f4);
			Ear1.render(ms, vb, i1, i2, f1, f2, f3, f4);
			Ear2.render(ms, vb, i1, i2, f1, f2, f3, f4);
			Nose.render(ms, vb, i1, i2, f1, f2, f3, f4);
		}

		private void setRotation(ModelRenderer model, float x, float y, float z) {
			model.rotateAngleX = x;
			model.rotateAngleY = y;
			model.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.Leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
			this.Leg3.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.Leg4.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
			this.WolfHead.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.WolfHead.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.Nose.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.Nose.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.Leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
			this.Ear1.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.Ear1.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.Ear2.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.Ear2.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.Mane.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.Mane.rotateAngleX = f4 / (180F / (float) Math.PI);
		}
	}
}
