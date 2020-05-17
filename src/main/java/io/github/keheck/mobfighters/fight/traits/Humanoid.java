package io.github.keheck.mobfighters.fight.traits;

import io.github.keheck.mobfighters.fight.fighters.Fighter;

public class Humanoid extends Trait
{

    public Humanoid(Fighter owner)
    {
        super(owner, ActivationType.NO_EFFECT);
    }

    @Override
    public void applyEffect(Fighter[] ownParty, Fighter[] enemyParty, ActivationType phase) {

    }
}
