package io.github.keheck.mobfighters.registry.entries;

import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.fight.moves.Move;
import io.github.keheck.mobfighters.util.IMoveFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public final class MoveEntry implements IForgeRegistryEntry<MoveEntry>
{
    private ResourceLocation registryName;
    private IMoveFactory factory;

    public MoveEntry(IMoveFactory factory) { this.factory = factory; }

    @Override
    public MoveEntry setRegistryName(ResourceLocation name)
    {
        this.registryName = name;
        return this;
    }

    public MoveEntry setRegistryName(String modID, String name)
    {
        return this.setRegistryName(new ResourceLocation(modID, name));
    }

    public Move onFighterInit(Fighter owner) { return factory.build(owner); }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() { return this.registryName; }

    @Override
    public Class<MoveEntry> getRegistryType() { return MoveEntry.class; }
}
