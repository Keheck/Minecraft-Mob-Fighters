package io.github.keheck.mobfighters.util;

import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;
import io.github.keheck.mobfighters.registry.entries.MoveEntry;
import io.github.keheck.mobfighters.registry.entries.TraitEntry;

public interface IFighterFactory
{
    Fighter getFighter(FighterEntry entry, MoveEntry[] startMoves, TraitEntry[] traits);
}
