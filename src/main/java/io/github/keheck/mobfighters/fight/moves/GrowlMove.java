package io.github.keheck.mobfighters.fight.moves;

import io.github.keheck.mobfighters.fight.fighters.Fighter;

import javax.annotation.Nullable;

public class GrowlMove extends Move
{
    public GrowlMove(@Nullable Fighter owner)
    {
        super(owner, 20);
    }

    @Override
    public void perform(Fighter[] ownParty, Fighter[] enemyParty)
    {

    }
}
