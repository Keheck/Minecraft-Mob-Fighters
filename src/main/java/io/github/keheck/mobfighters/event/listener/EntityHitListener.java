package io.github.keheck.mobfighters.event.listener;

import io.github.keheck.mobfighters.MobFighters;
import io.github.keheck.mobfighters.registry.holders.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber
public class EntityHitListener
{
    // key: challenger, value: challenged
    private static HashMap<UUID, UUID> pendingChallenges = new HashMap<>();

    @SubscribeEvent
    public static void onEntityHit(LivingAttackEvent event)
    {
        if(!event.getEntity().getEntityWorld().isRemote())
        {
            if(event.getSource().getImmediateSource() != null)
            {
                Entity source = event.getSource().getImmediateSource();
                Entity hitEntity = event.getEntity();
                World world = hitEntity.getEntityWorld();
                boolean fighterHit = true;
                boolean pvp = source instanceof PlayerEntity && hitEntity instanceof PlayerEntity;

                UUID challenger = source.getUniqueID();
                UUID challenged = hitEntity.getUniqueID();

                if(pvp)
                {
                    PlayerEntity pChallenger = (PlayerEntity)source;
                    PlayerEntity pChallenged = (PlayerEntity)hitEntity;

                    //noinspection ConstantConditions
                    if(pChallenger.getHeldItem(Hand.MAIN_HAND).getItem() != Items.fight_initiator)
                    {
                        MobFighters.LOGGER.info("No initiator was used!");
                        return;
                    }

                    boolean challengeAccept = pendingChallenges.get(challenged) == challenger;

                    if(challengeAccept)
                    {
                        pChallenged.sendStatusMessage(new StringTextComponent(pChallenger.getDisplayName().getString() + " accepted your challenge!"), true);
                        pendingChallenges.remove(challenged);

                        Set<UUID> keySet = pendingChallenges.keySet();

                        for(UUID uuid : keySet)
                        {
                            if(pendingChallenges.get(uuid) == challenger)
                                pendingChallenges.remove(uuid);
                        }
                    }
                    else
                    {
                        pendingChallenges.put(challenger, challenged);
                        pChallenged.sendStatusMessage(new StringTextComponent(pChallenger.getDisplayName().getString() + " challenged you!"), true);
                    }
                }

                MobFighters.LOGGER.debug(hitEntity.getUniqueID());
                MobFighters.LOGGER.debug(hitEntity.getEntityId());
            }
        }
    }
}
