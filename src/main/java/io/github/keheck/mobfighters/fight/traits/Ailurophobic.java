package io.github.keheck.mobfighters.fight.traits;

import io.github.keheck.mobfighters.fight.traits.effects.IChanceModifier;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;

public class Ailurophobic extends Trait implements IChanceModifier
{
    public Ailurophobic(FighterEntry owner)
    {
        super(owner, ActivationType.MOVE);
    }

    @Override
    public float getChance() { return -0.3f; }

    @Override
    public void applyEffect(FighterEntry[] ownParty, FighterEntry[] enemyParty, ActivationType phase)
    {

    }
}
