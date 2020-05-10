package io.github.keheck.mobfighters.item;

import io.github.keheck.mobfighters.MobFighters;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FightInitiatorItem extends Item
{
    public FightInitiatorItem()
    {
        super(new Properties().group(ItemGroup.MISC));
        setRegistryName(MobFighters.getModLocation("fight_initiator"));
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
