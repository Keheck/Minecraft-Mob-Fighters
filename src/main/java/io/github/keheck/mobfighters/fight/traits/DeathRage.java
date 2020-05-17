package io.github.keheck.mobfighters.fight.traits;

import io.github.keheck.mobfighters.fight.fighters.Fighter;

public class DeathRage extends Trait
{

    public DeathRage(Fighter owner)
    {
        super(owner, ActivationType.MOVE);
    }

    @Override
    public void applyEffect(Fighter[] ownParty, Fighter[] enemyParty, ActivationType phase) {

    }
}
