package io.github.keheck.mobfighters.registry.entries;

import io.github.keheck.mobfighters.MobFighters;
import io.github.keheck.mobfighters.entity.Fighter;
import io.github.keheck.mobfighters.fighter.moves.Move;
import io.github.keheck.mobfighters.fighter.traits.Trait;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraft.entity.Entity;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Objects;

/**
 * This class is used for all fighters.
 *
 * <ul>
 *     <li>{@code fighterModel}: The model taken from the original {@link Entity}</li>
 *     <li>{@code standardMoves}: An array of {@link Move}s the fighters has after being caught</li>
 *     <li>{@code learnableMoves}: An array of {@link Move}s the fighter can learn, no matter the means.</li>
 *     <li>{@code traits}: An array of {@link Trait}s the fighter has</li>
 * </ul>
 *
 * Note 1: When {@code learnableMoves} is {@code null}, the fighter can learn any move. If you want the fighter to be
 *         unable to learn any move, use the {@link #NO_MOVES} array.
 */
public final class FighterEntry implements IForgeRegistryEntry<FighterEntry>
{
    private final String registryName;
    private final String name;
    private final Move[] standardMoves;
    private final Move[] learnableMoves;
    private final Trait[] traits;
    private final Class<? extends Entity> representative;
    private final EntityRenderer<Fighter> renderer;

    public static final Move[] NO_MOVES = new Move[0];

    private static final FighterEntryBuilder BUILDER = new FighterEntryBuilder();
    private static int ID = 0;

    private FighterEntry(String registryName, String name, Move[] standardMoves, Move[] learnableMoves, Class<? extends Entity> representative,
                         EntityRenderer<Fighter> renderer, Trait... traits)
    {
        this.registryName = registryName;
        this.name = name;
        this.standardMoves = standardMoves;
        this.learnableMoves = learnableMoves;
        this.traits = traits;
        this.representative = representative;
        this.renderer = renderer;
    }

    public static int getID() { return ID; }

    /**
     * The override of this method makes the {@code FighterEntries} immutable :P
     * @deprecated All fighters are registered via the {@link FighterEntryBuilder}
     */
    @Override
    @Deprecated
    public FighterEntry setRegistryName(ResourceLocation name) { return this; }

    @Override
    public ResourceLocation getRegistryName() { return MobFighters.getModLocation(registryName); }

    @Override
    public Class<FighterEntry> getRegistryType() { return FighterEntry.class; }

    public static FighterEntryBuilder getBuilder(String name) { return BUILDER.init(name); }

    public static class FighterEntryBuilder
    {
        private Move[] moves;
        private Move[] learnables;
        private Trait[] traits;
        private String name;
        private String registryName;
        private Class<? extends Entity> representative;
        private EntityRenderer<Fighter> renderer;

        private FighterEntryBuilder() {}

        /**
         * Initializes the builder for the next fighter. This is done so that no left-over data of the previous fighter
         * is carried/copied over to the next fighter entry.
         *
         * @param name This is the name to be used in the game, NOT the registry name.
         * @return the {@code Builder singleton} to start building the fighter.
         */
        private FighterEntryBuilder init(String name)
        {
            this.moves = new Move[4];
            this.learnables = new Move[64];
            this.traits = new Trait[4];
            this.name = name;
            this.registryName = name.toLowerCase();
            this.representative = null;
            this.renderer = null;
            return this;
        }

        public FighterEntryBuilder setMoves(Move... moves)
        {
            if(moves == null)
                moves = new Move[this.moves.length];

            this.moves = moves;
            return this;
        }

        public FighterEntryBuilder setLearnables(Move... learnables)
        {
            this.learnables = learnables;
            return this;
        }

        public FighterEntryBuilder setTraits(Trait... traits)
        {
            if(traits == null)
                traits = new Trait[this.traits.length];

            this.traits = traits;
            return this;
        }

        public FighterEntryBuilder setRepresentative(Class<? extends Entity> representative)
        {
            Objects.requireNonNull(representative);
            this.representative = representative;
            return this;
        }

        public FighterEntryBuilder setRegistryName(String registryName)
        {
            this.registryName = registryName;
            return this;
        }

        public FighterEntryBuilder setRenderer(EntityRenderer<Fighter> renderer)
        {
            this.renderer = renderer;
            return this;
        }

        public FighterEntryBuilder addMove(Move move)
        {
            if(move != null)
            {
                int i = ArrayUtils.indexOf(moves, null);

                if(i == -1)
                    MobFighters.LOGGER.warn(String.format("Tried to add move to the fighter %s, but standard moves were full (max. 4)", name));
                else
                    moves[i] = move;
            }

            return this;
        }

        public FighterEntryBuilder addLearnable(Move learnable)
        {
            if(learnable != null)
            {
                int i = ArrayUtils.indexOf(learnables, null);

                if(i == -1)
                {
                    Move[] newLearnables = new Move[learnables.length + 16];
                    System.arraycopy(learnables, 0, newLearnables, 0, learnables.length);
                    i = ArrayUtils.indexOf(newLearnables, null);
                    newLearnables[i] = learnable;
                    learnables = newLearnables;
                }
                else
                    learnables[i] = learnable;
            }

            return this;
        }

        public FighterEntryBuilder addTrait(Trait trait)
        {
            if(trait != null)
            {
                int i = ArrayUtils.indexOf(traits, null);

                if(i == -1)
                    MobFighters.LOGGER.warn(String.format("Trying to add trait to the fighter %s, but trait slots are ful (max. 4)", name));
                else
                    traits[i] = trait;
            }

            return this;
        }

        public FighterEntry build()
        {
            if(representative == null || renderer == null)
            {
                IllegalStateException e = new IllegalStateException("Either the representative class or the renderer had a null value for the fighter " + name);
                e.printStackTrace();
                throw e;
            }

            return new FighterEntry(registryName, name, moves, learnables, representative, renderer, traits);
        }
    }
}
