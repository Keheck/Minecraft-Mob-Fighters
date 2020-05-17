package io.github.keheck.mobfighters.fight.traits;

import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.fight.traits.effects.IChanceModifier;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;

public class Ailurophobic extends Trait implements IChanceModifier
{
    public Ailurophobic(Fighter owner)
    {
        super(owner, ActivationType.MOVE);
    }

    @Override
    public float getChance() { return -0.3f; }

    @Override
    public void applyEffect(Fighter[] ownParty, Fighter[] enemyParty, ActivationType phase)
    {

    }
}
