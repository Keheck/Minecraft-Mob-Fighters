package io.github.keheck.mobfighters.entity;

import io.github.keheck.mobfighters.MobFighters;
import io.github.keheck.mobfighters.item.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class MobBallEntity extends ProjectileItemEntity
{
    public MobBallEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_)
    {
        super(p_i50155_1_, p_i50155_2_);
    }

    public MobBallEntity(double p_i50156_2_, double p_i50156_4_, double p_i50156_6_, World p_i50156_8_)
    {
        super(Entities.mob_ball, p_i50156_2_, p_i50156_4_, p_i50156_6_, p_i50156_8_);
    }

    public MobBallEntity(LivingEntity p_i50157_2_, World p_i50157_3_)
    {
        super(Entities.mob_ball, p_i50157_2_, p_i50157_3_);
    }

    @Override
    public void tick()
    {
        /*if(!world.isRemote && owner == null)
        {
            this.remove();
            return;
        }*/

        super.tick();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected Item func_213885_i() { return Items.mob_ball; }

    @Override
    @SuppressWarnings("NullableProblems")
    public ItemStack getItem() { return new ItemStack(this::func_213885_i); }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void onImpact(RayTraceResult result)
    {
        if(!world.isRemote())
            this.remove();
    }

    @Override
    public void remove()
    {
        MobFighters.LOGGER.info("Died!");
        super.remove();
    }
}
