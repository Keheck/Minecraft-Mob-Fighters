package io.github.keheck.mobfighters.fight.traits.effects;

import io.github.keheck.mobfighters.fight.moves.Move;

public interface IChanceModifier
{
    /**
     * @return a {@code float} value between 1 and -1. Positive values increase, negative values decrease the chance of
     *         landing a move. In case the {@link Move#canMiss()} method returns {@code true}, the starting
     *         chance is 1, or 100%. The used operation to modify the chance is addition, not multiplication.
     * @see Move#getChance()
     */
    float getChance();
}
