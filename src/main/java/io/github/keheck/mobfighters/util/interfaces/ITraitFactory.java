package io.github.keheck.mobfighters.util.interfaces;

import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.fight.traits.Trait;
import io.github.keheck.mobfighters.registry.entries.TraitEntry;

/**
 * This is a functional interface to help the {@link TraitEntry#onFighterBuild(Fighter)}
 * method construct a trait. It only takes the owner of the trait as a parameter and
 * returns a new instance a {@link Trait} subclass.
 */
public interface ITraitFactory
{
    Trait build(Fighter owner);
}
