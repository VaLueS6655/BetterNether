package paulevs.betternether.entity;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.EntityRegistry;
import paulevs.betternether.registry.SoundsRegistry;

public class EntityNaga extends HostileEntity implements RangedAttackMob
{
	public EntityNaga(EntityType<? extends EntityNaga> type, World world)
	{
		super(type, world);
		this.experiencePoints = 10;
	}

	@Override
	protected void initGoals()
	{
		this.targetSelector.add(1, new FollowTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));
		this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0D, 40, 20.0F));
		this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(5, new LookAroundGoal(this));
	}

	@Override
	protected void initAttributes()
	{
		super.initAttributes();
		this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(10.0);
		this.getAttributeInstance(EntityAttributes.FOLLOW_RANGE).setBaseValue(35.0);
		this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.23);
		this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(3.0);
		this.getAttributeInstance(EntityAttributes.ARMOR).setBaseValue(2.0);
	}

	@Override
	public void attack(LivingEntity target, float f)
	{
		EntityNagaProjectile projectile = new EntityNagaProjectile(EntityRegistry.NAGA_PROJECTILE, world);
		projectile.setPos(getX(), getEyeY(), getZ());
		projectile.setParams(this, target);
		world.spawnEntity(projectile);
		this.playSound(SoundsRegistry.MOB_NAGA_ATTACK, MHelper.randRange(3F, 5F, random), MHelper.randRange(0.75F, 1.25F, random));
	}

	@Override
	public EntityGroup getGroup()
	{
		return EntityGroup.UNDEAD;
	}
	
	@Override
	public boolean canHaveStatusEffect(StatusEffectInstance effect)
	{
		return effect.getEffectType() == StatusEffects.WITHER ? false : super.canHaveStatusEffect(effect);
	}
	
	@Override
	public SoundEvent getAmbientSound()
	{
		return SoundsRegistry.MOB_NAGA_IDLE;
	}
}