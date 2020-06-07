package io.github.keheck.mobfighters.util.interfaces;

import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.fight.moves.Move;
import io.github.keheck.mobfighters.registry.entries.MoveEntry;

/**
 * This is a functional interface to help the {@link MoveEntry#onFighterBuild(Fighter)}
 * method construct a trait. It only takes the owner of the move as a parameter and
 * returns a new instance a {@link Move} subclass.
 */
public interface IMoveFactory
{
    Move build(Fighter owner);
}
