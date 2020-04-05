package io.github.keheck.mobfighters.item;

import io.github.keheck.mobfighters.MobFighters;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MobBallItem extends Item
{
    public MobBallItem()
    {
        super(new Properties().maxStackSize(1));
        setRegistryName(MobFighters.getModLocation("mob_ball"));
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        if(!world.isRemote())
        {

        }

        return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
    }
}
