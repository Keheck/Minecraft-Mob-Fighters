package io.github.keheck.mobfighters.registry;

import io.github.keheck.mobfighters.MobFighters;
import io.github.keheck.mobfighters.entity.MobBallEntity;
import io.github.keheck.mobfighters.fight.moves.PounceMove;
import io.github.keheck.mobfighters.item.FightInitiatorItem;
import io.github.keheck.mobfighters.item.MobBallDeprecatedItem;
import io.github.keheck.mobfighters.item.MobBallItem;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;
import io.github.keheck.mobfighters.registry.entries.MoveEntry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.*;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MobFighters.MODID, bus = Bus.MOD)
public class Registry
{
    private static IForgeRegistry<FighterEntry> fighterRegistry;
    private static IForgeRegistry<MoveEntry> moveRegistry;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> itemRegister)
    {
        itemRegister.getRegistry().registerAll(
                new MobBallDeprecatedItem(),
                new MobBallItem(),
                new FightInitiatorItem());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> blockRegister)
    {
        blockRegister.getRegistry().registerAll();
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> entityTypeRegister)
    {
        IForgeRegistry<EntityType<?>> registry = entityTypeRegister.getRegistry();

        registry.register(EntityType.Builder
                .<MobBallEntity>create(MobBallEntity::new, EntityClassification.MISC)
                .size(.25f, .25f)
                .setUpdateInterval(1)
                .build("mob_ball")
                .setRegistryName(MobFighters.getModLocation("mob_ball")));
    }

    @SubscribeEvent
    public static void registerMoves(RegistryEvent.Register<MoveEntry> moveRegister)
    {
        moveRegister.getRegistry().registerAll
                (

                );
    }

    @SubscribeEvent
    public static void registerFighters(RegistryEvent.Register<FighterEntry> fighterEntryRegister)
    {
        fighterEntryRegister.getRegistry().registerAll();
    }

    @SubscribeEvent
    public static void registerRegistries(@SuppressWarnings("unused") RegistryEvent.NewRegistry newRegistry)
    {
        fighterRegistry = new RegistryBuilder<FighterEntry>()
                .setName(MobFighters.getModLocation("fighter"))
                .setMaxID(1023)
                .setDefaultKey(MobFighters.getModLocation("pig"))
                .setType(FighterEntry.class)
                .create();

        moveRegistry = new RegistryBuilder<MoveEntry>()
                .setName(MobFighters.getModLocation("move"))
                .setMaxID(255)
                .setDefaultKey(new ResourceLocation("pounce"))
                .setType(MoveEntry.class)
                .create();
    }

    public static IForgeRegistry<FighterEntry> getFighterRegistry() { return fighterRegistry; }

    public static IForgeRegistry<MoveEntry> getMoveRegistry() { return moveRegistry; }
}
