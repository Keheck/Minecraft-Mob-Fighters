package io.github.keheck.mobfighters.fight.traits;

import io.github.keheck.mobfighters.fight.fighters.Fighter;

public final class TraitEmpty extends Trait
{
    public TraitEmpty(Fighter owner) { super(owner); }

    @Override
    public void applyEffect(Fighter[] ownParty, Fighter[] enemyParty, ActivationType phase) { }
}
