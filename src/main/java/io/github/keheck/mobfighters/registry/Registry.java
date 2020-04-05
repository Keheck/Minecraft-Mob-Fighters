package io.github.keheck.mobfighters.registry;

import io.github.keheck.mobfighters.MobFighters;
import io.github.keheck.mobfighters.entity.Entities;
import io.github.keheck.mobfighters.entity.MobBallEntity;
import io.github.keheck.mobfighters.item.MobBallItem;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.*;

/**
 * <h3>How do I create a fighter?</h3>
 * <p>
 *     (A TL;DR is at the bottom) Well, my son, you do it throught the {@code RegistryEvent.Register<EntityType<?>>} event,
 *     of course! What, it doesn't make sense? <i>sigh</i>... you're right, it should be through the {@code FigherEntry}
 *     registry, but I want to automatically create a new entity for each fighter that is added, and you can't
 *     register entries to thier respective registry after the event of that registry has been called. For example,
 *     the registry event for the {@code Blocks} is fired before the {@code Item} registry event. This means that
 *     you can register items in the block handler, but you cannot register blocks in the item handler. What does
 *     that have to do with fighters? Well, the registry event for fighters gets called after the entity registry
 *     event has, so I can't register entities for fighters inside the fighter registry. I only can do it inside the
 *     entity registry. And to get your fighter registered, you should do it inside the entity registry event
 *     as well. You get the registry by calling {@link Registry#getFighterRegistry()}. Be sure the set the priorities
 *     of the events where you register your fighters between {@link EventPriority#LOWEST} and {@link EventPriority#HIGHEST},
 *     excluding both.
 * </p>
 * <p> TL;DR: You do it inside a entity registry handler. You can get the registry via {@link #getFighterRegistry()}. </p>
 */
@SuppressWarnings("unused") //Why does my IDEA plugin not mark the methods used?
@Mod.EventBusSubscriber(modid = MobFighters.MODID, bus = Bus.MOD)
public class Registry
{
    private static IForgeRegistry<FighterEntry> fighterRegistry;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> itemRegister)
    {
        itemRegister.getRegistry().register(new MobBallItem());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> blockRegister)
    {

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerFighters(RegistryEvent.Register<EntityType<?>> entityTypeRegister)
    {

    }

    /** Register non-fighter entities. */
    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> entityTypeRegister)
    {
        entityTypeRegister.getRegistry().registerAll(
                EntityType.Builder
                        .<MobBallEntity>create(MobBallEntity::new, EntityClassification.MISC)
                        .size(.25f, .25f)
                        .setCustomClientFactory((entity, world) -> new MobBallEntity(Entities.mob_ball, world))
                        .build("mob_ball")
                        .setRegistryName(MobFighters.getModLocation("mob_ball")));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void fetchFighters(RegistryEvent.Register<EntityType<?>> entityTypeRegister)
    {

    }

    @SubscribeEvent
    public static void registerRegistry(@SuppressWarnings("unused") RegistryEvent.NewRegistry newRegistry)
    {
        RegistryBuilder<FighterEntry> builder = new RegistryBuilder<>();
        fighterRegistry = builder
                .setName(MobFighters.getModLocation("fighters"))
                .setMaxID(1023)
                .setDefaultKey(MobFighters.getModLocation("mossingno"))
                .setType(FighterEntry.class)
                .create();
    }

    public static IForgeRegistry<FighterEntry> getFighterRegistry() { return fighterRegistry; }
}
