package io.github.keheck.mobfighters.registry.entries;

import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.fight.moves.Move;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;

/**
 * <p>
 *     Every instance of this class holds information on what an instance
 *     of the {@link Fighter} class should be able to do, e.g. what
 *     {@linkplain Move moves} can be added to said instance or what
 *     moves the instance holds to begin with.
 * </p>
 * <p>
 *     The registry name of every instance will always be the registry name
 *     of the {@link #entityType} field, so there can be quick access to
 *     the {@code FighterEntry} instance.
 * </p>
 */
public final class FighterEntry implements IForgeRegistryEntry<FighterEntry>
{
    private EntityType<? extends LivingEntity> entityType;
    /** This is a pool of the moves the Fighter has at the beginning */
    private Move[] movePool;
    private Move[] learnPool;

    /**
     * @return this instance
     * @deprecated This class doesn't use this method, since the
     *             {@link #getRegistryName()} method returns the
     *             registry name of the {@link #entityType} field.
     */
    @Override
    @Deprecated
    public FighterEntry setRegistryName(ResourceLocation name) { return this; }

    public void applyData(ResourceLocation[] movePool, ResourceLocation[] learnPool)
    {

    }

    /**
     * Not to be confused with the {@link FighterEntryBuilder}, this method returns an instance of
     * {@link Fighter} or one of it's subclasses
     *
     * @return a new {@link Fighter} instance.
     */
    /*public Fighter buildInstance()
    {

    }*/

    /**
     * @return the registry name of {@link #entityType} by calling it's
     *         {@linkplain EntityType#getRegistryName() getRegistryName()} method
     */
    @Nullable
    @Override
    public ResourceLocation getRegistryName() { return entityType.getRegistryName(); }

    @Override
    public Class<FighterEntry> getRegistryType() { return FighterEntry.class; }

    public static class FighterEntryBuilder
    {
        private static final FighterEntryBuilder BUILDER = new FighterEntryBuilder();

        public static FighterEntryBuilder getBuilder() { return BUILDER; }


    }
}
