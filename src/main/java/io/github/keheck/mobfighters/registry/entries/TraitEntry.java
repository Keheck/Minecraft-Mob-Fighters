package io.github.keheck.mobfighters.registry.entries;

import io.github.keheck.mobfighters.fight.traits.Trait;
import io.github.keheck.mobfighters.util.ITraitFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public final class TraitEntry implements IForgeRegistryEntry<TraitEntry>
{
    private ResourceLocation registryName;
    private ITraitFactory factory;

    public TraitEntry(ITraitFactory factory)
    {
        this.factory = factory;
    }

    public Trait onFightInit(FighterEntry owner) { return factory.build(owner); }

    @Override
    public TraitEntry setRegistryName(ResourceLocation name)
    {
        this.registryName = name;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() { return registryName; }

    @Override
    public Class<TraitEntry> getRegistryType() { return TraitEntry.class; }
}
