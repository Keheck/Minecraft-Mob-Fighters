package io.github.keheck.mobfighters.fight.fighters;

import io.github.keheck.mobfighters.fight.moves.Move;
import io.github.keheck.mobfighters.fight.traits.Trait;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;
import io.github.keheck.mobfighters.registry.entries.MoveEntry;
import io.github.keheck.mobfighters.registry.entries.TraitEntry;
import org.apache.commons.lang3.ArrayUtils;

public final class Fighter
{
    private FighterEntry entry;

    private Trait[] traits;
    private Move[] moves;

    private final int hp, atk, def, spd;

    public Fighter(FighterEntry entry, MoveEntry[] startMoves, TraitEntry[] traits, int hp, int atk, int def, int spd)
    {
        this.entry = entry;

        this.traits = new Trait[traits.length];
        this.moves = new Move[4];

        for(int i = 0; i < traits.length; i++)
            this.traits[i] = traits[i].onFighterBuild(this);

        for(int i = 0; i < startMoves.length; i++)
            this.moves[i] = startMoves[i].onFighterBuild(this);

        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
    }

    public FighterEntry getEntry() { return entry; }

    /**
     * @param trait The trait to be tested.
     * @return whether {@link #traits} contains the trait parameter.
     */
    public boolean hasTrait(Trait trait) { return ArrayUtils.contains(traits, trait); }

    public boolean hasMove(MoveEntry entry)
    {
        for(Move move : moves)
            if(entry.onFighterBuild(this).equals(move))
                return true;

        return false;
    }
}
