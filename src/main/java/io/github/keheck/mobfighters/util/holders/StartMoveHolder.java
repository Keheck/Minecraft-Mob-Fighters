package io.github.keheck.mobfighters.util.holders;

import io.github.keheck.mobfighters.registry.entries.MoveEntry;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;

/**
 * This is a helper class to better keep track
 * of the starting move pool of a {@link FighterEntry}.
 */
public class StartMoveHolder
{
    private final MoveEntry entry;
    private final float probability;

    public StartMoveHolder(MoveEntry entry, float probability)
    {
        this.entry = entry;
        this.probability = probability;
    }

    /**
     * This method compares the probabilities of two traits against
     * each other. Like the {@link Float#compare(float, float)} method,
     * but the result is flipped. NaN and positive/negative infinity are
     * not expected.
     *
     * @param h1 the first holder to be compared
     * @param h2 the second holder to be compared
     * @return
     * <ul>
     *     <li>{@code 0} if both probabilities are equal</li>
     *     <li>{@code 1} if h1 is less likely than h2</li>
     *     <li>{@code -1} if h1 is more likely than h2</li>
     * </ul>
     */
    public static int sort(StartMoveHolder h1, StartMoveHolder h2) { return Float.compare(h2.probability, h1.probability); }

    /** @return the {@link MoveEntry} to be remembered */
    public MoveEntry getEntry() { return entry; }

    /** @return the probability that the entry is going to be a starting move. */
    public float getProbability() { return probability; }
}
