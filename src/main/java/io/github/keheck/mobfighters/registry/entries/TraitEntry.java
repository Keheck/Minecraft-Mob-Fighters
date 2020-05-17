package io.github.keheck.mobfighters.registry.entries;

import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.fight.traits.INeedExtra;
import io.github.keheck.mobfighters.fight.traits.Trait;
import io.github.keheck.mobfighters.util.ITraitFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public final class TraitEntry implements IForgeRegistryEntry<TraitEntry>
{
    private ResourceLocation registryName;
    private final ITraitFactory factory;
    private final INeedExtra extra;

    private final boolean needsExtra;

    public TraitEntry(ITraitFactory factory) { this(factory, null); }

    public TraitEntry(ITraitFactory factory, @Nullable INeedExtra extra)
    {
        this.needsExtra = extra != null;
        this.extra = extra;
        this.factory = factory;
    }

    public Trait onFighterBuild(Fighter owner) { return factory.build(owner); }

    /**
     * Returns the field {@link #extra}, containing information on whether the trait needs extra
     * <b>static</b> information.
     * @return {@link #extra}, is {@code null} if {@link #needsExtra()} returns {@code false}.
     */
    @Nullable
    public INeedExtra getExtra() { return extra; }

    @Override
    public TraitEntry setRegistryName(ResourceLocation name)
    {
        this.registryName = name;
        return this;
    }

    public TraitEntry setRegistryName(String id, String name)
    {
        return this.setRegistryName(new ResourceLocation(id, name));
    }

    /**
     * Indicates whether the {@code .json} file of a fighter requires extra information
     * in the json file.
     * @return {@code true} if the trait needs extra JSON info, {@code false} otherwise.
     */
    public boolean needsExtra() { return needsExtra; }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() { return registryName; }

    @Override
    public Class<TraitEntry> getRegistryType() { return TraitEntry.class; }
}
