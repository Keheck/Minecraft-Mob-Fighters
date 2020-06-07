package io.github.keheck.mobfighters.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

@SuppressWarnings("NullableProblems")
public abstract class AbstractTrainer extends LivingEntity
{
    protected AbstractTrainer(EntityType<? extends LivingEntity> type, World world)
    {
        super(type, world);
    }



    @Override
    public final Iterable<ItemStack> getArmorInventoryList() { return NonNullList.withSize(0, ItemStack.EMPTY); }

    @Override
    public final ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) { return ItemStack.EMPTY; }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) { }

    @Override
    public HandSide getPrimaryHand() { return HandSide.RIGHT; }
}
