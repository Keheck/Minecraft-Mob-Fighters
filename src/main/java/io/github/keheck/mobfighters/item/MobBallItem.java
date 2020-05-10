package io.github.keheck.mobfighters.item;

import io.github.keheck.mobfighters.MobFighters;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class MobBallItem extends Item
{
    public MobBallItem()
    {
        super(new Properties().maxStackSize(16).group(ItemGroup.MISC));
        setRegistryName(MobFighters.getModLocation("mob_ball"));
    }
}
