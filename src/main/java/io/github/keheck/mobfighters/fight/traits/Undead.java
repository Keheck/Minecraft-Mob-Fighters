package io.github.keheck.mobfighters.fight.traits;

import io.github.keheck.mobfighters.fight.fighters.Fighter;

public class Undead extends Trait
{

    public Undead(Fighter owner)
    {
        super(owner, ActivationType.STANDBY);
    }

    @Override
    public void applyEffect(Fighter[] ownParty, Fighter[] enemyParty, ActivationType phase)
    {

    }
}
