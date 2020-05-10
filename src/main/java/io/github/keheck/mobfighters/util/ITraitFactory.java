package io.github.keheck.mobfighters.util;

import io.github.keheck.mobfighters.fight.traits.Trait;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;

public interface ITraitFactory
{
    Trait build(FighterEntry owner);
}
