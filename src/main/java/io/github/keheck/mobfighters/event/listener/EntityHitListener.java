package io.github.keheck.mobfighters.event.listener;

import io.github.keheck.mobfighters.item.Items;
import io.github.keheck.mobfighters.gui.screen.FightScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityHitListener
{
    @SubscribeEvent
    public static void onEntityHit(LivingAttackEvent event)
    {
        Entity source = event.getSource().getImmediateSource();
        Entity hitEntity = event.getEntity();
        boolean onClient = hitEntity.getEntityWorld().isRemote();
        boolean fighterHit = true;

        if(source instanceof PlayerEntity && fighterHit &&
                //true if the player holds mobfighters:fight_initiator
                //noinspection ConstantConditions
                ((PlayerEntity)source).getHeldItem(Hand.MAIN_HAND).getItem().getRegistryName().equals(Items.fight_initiator.getRegistryName()))
        {

            event.setCanceled(true);

            if(onClient)
            {
                Minecraft.getInstance().displayGuiScreen(new FightScreen());
            }
            else
            {

            }
        }
    }
}
