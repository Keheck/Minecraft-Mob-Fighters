package io.github.keheck.mobfighters.fight.moves;

import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;

public class PounceMove extends Move
{
    public PounceMove(Fighter owner) { super(owner, 30); }

    @Override
    public void perform(Fighter[] ownParty, Fighter[] enemyParty) { }
}
