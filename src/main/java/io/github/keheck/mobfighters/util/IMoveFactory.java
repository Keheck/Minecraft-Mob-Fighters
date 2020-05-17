package io.github.keheck.mobfighters.util;

import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.fight.moves.Move;

public interface IMoveFactory
{
    Move build(Fighter owner);
}
