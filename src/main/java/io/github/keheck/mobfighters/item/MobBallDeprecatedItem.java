package io.github.keheck.mobfighters.item;

import io.github.keheck.mobfighters.MobFighters;
import io.github.keheck.mobfighters.entity.MobBallEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class MobBallDeprecatedItem extends Item
{
    public MobBallDeprecatedItem()
    {
        super(new Properties().maxStackSize(1));
        setRegistryName(MobFighters.MODID, "mob_ball_deprecated");
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(!itemstack.hasTag())
            itemstack.getOrCreateTag().putString("entity", "null");
        itemstack.shrink(1);

        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote())
        {
            MobBallEntity mobBall = new MobBallEntity(worldIn, playerIn);
            mobBall.func_213884_b(itemstack);
            mobBall.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.addEntity(mobBall);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, new ItemStack(Items.AIR));
    }
}
