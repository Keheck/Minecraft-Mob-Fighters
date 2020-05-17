package io.github.keheck.mobfighters.fight.traits;

import io.github.keheck.mobfighters.fight.fighters.Fighter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WaterReactive extends Trait
{

    public WaterReactive(Fighter owner)
    {
        super(owner, ActivationType.STANDBY);
    }

    @Override
    public void applyEffect(Fighter[] ownParty, Fighter[] enemyParty, ActivationType phase)
    {

    }

    public static Map<String, Class<?>> getExtraInfo()
    {
        final HashMap<String, Class<?>> needyInfo = new HashMap<>();
        needyInfo.put("fighter", String.class);
        return Collections.unmodifiableMap(needyInfo);
    }
}
