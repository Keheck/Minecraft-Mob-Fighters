package io.github.keheck.mobfighters.util;

import io.github.keheck.mobfighters.MobFighters;
import io.github.keheck.mobfighters.item.MobBallItem;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = MobFighters.MODID, bus = Bus.MOD)
public class Registry
{
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> registry)
    {
        registry.getRegistry().register(new MobBallItem().setRegistryName(MobFighters.MODID, "mob_ball"));
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {

    }
}
