package io.github.keheck.mobfighters.util;

import io.github.keheck.mobfighters.fight.moves.Move;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;

public interface IMoveFactory
{
    Move build(FighterEntry owner, int maxUses);
}
