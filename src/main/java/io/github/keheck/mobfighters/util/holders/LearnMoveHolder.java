package io.github.keheck.mobfighters.util.holders;

import io.github.keheck.mobfighters.registry.entries.MoveEntry;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;

/**
 * This is a helper class to better keep track
 * of the learnable move pool of a {@link FighterEntry}.
 */
public class LearnMoveHolder
{
    private final MoveEntry entry;
    private final float probability;
    private final int level;

    public LearnMoveHolder(MoveEntry entry, float probability, int level)
    {
        this.entry = entry;
        this.probability = probability;
        this.level = level;
    }

    /**
     * This method compares the probabilities of two traits against
     * each other. Like the {@link Float#compare(float, float)} method,
     * but the result is flipped. NaN and positive/negative infinity are
     * not expected.
     *
     * @param l1 the first holder to be compared
     * @param l2 the second holder to be compared
     * @return
     * <ul>
     *     <li>{@code 0} if both probabilities are equal</li>
     *     <li>{@code 1} if l1 is less likely than l2</li>
     *     <li>{@code -1} if l1 is more likely than l2</li>
     * </ul>
     */
    public static int sort(LearnMoveHolder l1, LearnMoveHolder l2) { return Float.compare(l2.probability, l1.probability); }

    /** @return the level at which the move can be learned. */
    public int getLevel() { return level; }

    /** @return the {@link MoveEntry} to be remembered */
    public MoveEntry getEntry() { return entry; }

    /** @return the probability that the entry is going to be a starting move. */
    public float getProbability() { return probability; }
}
