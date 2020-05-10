package io.github.keheck.mobfighters.fight.moves;

import io.github.keheck.mobfighters.registry.entries.FighterEntry;

public class PounceMove extends Move
{
    public PounceMove(FighterEntry owner, int maxMoves) { super(owner, maxMoves); }

    @Override
    public void perform(FighterEntry[] ownParty, FighterEntry[] enemyParty) { }
}
