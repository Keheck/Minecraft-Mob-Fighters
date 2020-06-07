package io.github.keheck.mobfighters.registry;

import io.github.keheck.mobfighters.entity.MobBallEntity;
import io.github.keheck.mobfighters.fight.moves.GrowlMove;
import io.github.keheck.mobfighters.fight.moves.PounceMove;
import io.github.keheck.mobfighters.fight.traits.*;
import io.github.keheck.mobfighters.item.FightInitiatorItem;
import io.github.keheck.mobfighters.item.MobBallDeprecatedItem;
import io.github.keheck.mobfighters.item.MobBallItem;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;
import io.github.keheck.mobfighters.registry.entries.MoveEntry;
import io.github.keheck.mobfighters.registry.entries.TraitEntry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.*;

import static io.github.keheck.mobfighters.MobFighters.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Bus.MOD)
public class Registry
{
    private static IForgeRegistry<FighterEntry> fighterRegistry;
    private static IForgeRegistry<MoveEntry> moveRegistry;
    private static IForgeRegistry<TraitEntry> traitRegistry;

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
                .setRegistryName(MODID, "mob_ball"));
    }

    @SubscribeEvent
    public static void registerTraits(RegistryEvent.Register<TraitEntry> traitRegister)
    {
        traitRegister.getRegistry().registerAll
                (
                        new TraitEntry(BatteryTrait::new, BatteryTrait::getExtraInfo).setRegistryName(MODID, "battery"),
                        new TraitEntry(Ailurophobic::new).setRegistryName(MODID, "ailurophobic"),
                        new TraitEntry(DeathRage::new).setRegistryName(MODID, "death_rage"),
                        new TraitEntry(Humanoid::new).setRegistryName(MODID, "humanoid"),
                        new TraitEntry(Undead::new).setRegistryName(MODID, "undead"),
                        new TraitEntry(TraitEmpty::new).setRegistryName(MODID, "empty"),
                        new TraitEntry(WaterReactive::new, WaterReactive::getExtraInfo).setRegistryName(MODID, "water_reactive")
                );
    }

    @SubscribeEvent
    public static void registerMoves(RegistryEvent.Register<MoveEntry> moveRegister)
    {
        moveRegister.getRegistry().registerAll
                (
                        new MoveEntry(GrowlMove::new).setRegistryName(MODID, "growl"),
                        new MoveEntry(PounceMove::new).setRegistryName(MODID, "pounce")
                );
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerFighters(RegistryEvent.Register<FighterEntry> fighterRegister)
    {
        fighterRegister.getRegistry().registerAll
                (
                        new FighterEntry(EntityType.ZOMBIE),
                        new FighterEntry(EntityType.PIG)
                );
    }

    @SubscribeEvent
    public static void registerRegistries(@SuppressWarnings("unused") RegistryEvent.NewRegistry newRegistry)
    {
        fighterRegistry = new RegistryBuilder<FighterEntry>()
                .setName(new ResourceLocation(MODID, "fighter"))
                .setMaxID(1023)
                .setDefaultKey(new ResourceLocation(MODID, "zombie"))
                .setType(FighterEntry.class)
                .create();

        moveRegistry = new RegistryBuilder<MoveEntry>()
                .setName(new ResourceLocation(MODID, "move"))
                .setMaxID(1023)
                .setDefaultKey(new ResourceLocation(MODID, "pounce"))
                .setType(MoveEntry.class)
                .create();

        traitRegistry = new RegistryBuilder<TraitEntry>()
                .setName(new ResourceLocation(MODID, "trait"))
                .setMaxID(1023)
                .setDefaultKey(new ResourceLocation(MODID, "empty"))
                .setType(TraitEntry.class)
                .create();
    }

    public static IForgeRegistry<FighterEntry> getFighterRegistry() { return fighterRegistry; }

    public static IForgeRegistry<MoveEntry> getMoveRegistry() { return moveRegistry; }

    public static IForgeRegistry<TraitEntry> getTraitRegistry() { return traitRegistry; }
}
