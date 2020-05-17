package io.github.keheck.mobfighters.util;

import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.fight.traits.Trait;

public interface ITraitFactory
{
    Trait build(Fighter owner);
}
