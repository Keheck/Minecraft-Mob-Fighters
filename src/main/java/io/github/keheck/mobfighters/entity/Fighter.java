package io.github.keheck.mobfighters.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public final class Fighter extends LivingEntity
{
    private final NonNullList<ItemStack> fighterInv = NonNullList.withSize(16, ItemStack.EMPTY);

    protected Fighter(World p_i48577_2_)
    {
        this(Entities.FIGHTER, p_i48577_2_);
    }

    protected Fighter(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_)
    {
        super(p_i48577_1_, p_i48577_2_);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Iterable<ItemStack> getArmorInventoryList()
    {
        return fighterInv;
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn)
    {
        return fighterInv.get(slotIn.getSlotIndex());
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, @SuppressWarnings("NullableProblems") ItemStack stack)
    {
        fighterInv.set(slotIn.getSlotIndex(), stack);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public HandSide getPrimaryHand()
    {
        return HandSide.RIGHT;
    }
}
