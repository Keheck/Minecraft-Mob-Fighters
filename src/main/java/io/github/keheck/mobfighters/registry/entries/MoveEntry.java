package io.github.keheck.mobfighters.registry.entries;

import io.github.keheck.mobfighters.fight.moves.Move;
import io.github.keheck.mobfighters.util.IMoveFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public final class MoveEntry implements IForgeRegistryEntry<MoveEntry>
{
    private ResourceLocation registryName;
    private IMoveFactory factory;
    private int maxUses = 20;

    public MoveEntry(IMoveFactory factory) { this.factory = factory; }

    public MoveEntry setMaxUses(int maxUses)
    {
        this.maxUses = maxUses;
        return this;
    }

    @Override
    public MoveEntry setRegistryName(ResourceLocation name)
    {
        this.registryName = name;
        return this;
    }

    public Move onFightInit(FighterEntry owner) { return factory.build(owner, maxUses); }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() { return this.registryName; }

    @Override
    public Class<MoveEntry> getRegistryType() { return MoveEntry.class; }
}
