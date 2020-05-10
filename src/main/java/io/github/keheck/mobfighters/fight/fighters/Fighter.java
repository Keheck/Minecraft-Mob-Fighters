package io.github.keheck.mobfighters.fight.fighters;

import io.github.keheck.mobfighters.fight.traits.Trait;

public abstract class Fighter
{

    /**
     * Since the "contract" of fighters are that if the type of two
     * fighters are the same, they have the same traits, without exception.
     *
     * @return an array of traits for the Fighter.
     */
    public abstract Trait[] getTraits();
}
