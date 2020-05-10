package io.github.keheck.mobfighters.entity;

import io.github.keheck.mobfighters.MobFighters;
import io.github.keheck.mobfighters.item.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class MobBallEntity extends ProjectileItemEntity
{
    @SuppressWarnings("unused")
    public MobBallEntity( EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_)
    {
        super(Entities.mob_ball, p_i50155_2_);
    }

    public MobBallEntity(double p_i50156_2_, double p_i50156_4_, double p_i50156_6_, World p_i50156_8_)
    {
        super(Entities.mob_ball, p_i50156_2_, p_i50156_4_, p_i50156_6_, p_i50156_8_);
    }

    public MobBallEntity(World p_i50157_3_, LivingEntity p_i50157_2_)
    {
        super(Entities.mob_ball, p_i50157_2_, p_i50157_3_);
    }

    @Override
    protected Item func_213885_i() { return Items.mob_ball; }

    @Override
    public ItemStack getItem() { return new ItemStack(this::func_213885_i); }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if(!world.isRemote())
        {
            if(result.getType() != RayTraceResult.Type.MISS)
            {
                Vec3d vec3d = result.getHitVec();
                ItemStack stack = new ItemStack(Items.mob_ball_deprecated);
                ItemStack meAsStack = this.func_213882_k();
                CompoundNBT compound = meAsStack.getTag();

                if(compound.getString("entity").equals("null"))
                {
                    if(result.getType() == RayTraceResult.Type.ENTITY)
                    {
                        EntityRayTraceResult entityResult = (EntityRayTraceResult)result;
                        Entity entity = entityResult.getEntity();

                        if(Entities.hasFighterEquivalent(entity))
                        {
                            CompoundNBT nbt = stack.getOrCreateTag();
                            CompoundNBT entityData = new CompoundNBT();
                            entity.writeUnlessRemoved(entityData);
                            nbt.putString("entity", entity.getType().getRegistryName().toString());
                            nbt.put("data", entityData);
                            entity.remove();
                        }
                    }
                }
                else
                {
                    IForgeRegistry<EntityType<?>> entityRegistry = ForgeRegistries.ENTITIES;
                    EntityType<?> entityType = entityRegistry.getValue(new ResourceLocation(compound.getString("entity")));
                    Entity entity = entityType.create(world);
                    entity.read(compound.getCompound("data"));
                    entity.setPosition(vec3d.x, vec3d.y+0.5, vec3d.z);
                    world.addEntity(entity);
                }

                ItemEntity item = new ItemEntity(world, vec3d.x, vec3d.y, vec3d.z, stack);
                world.addEntity(item);
                this.remove();
            }
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
}
