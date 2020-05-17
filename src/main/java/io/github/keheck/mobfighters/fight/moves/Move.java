package io.github.keheck.mobfighters.fight.moves;

import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class Move
{
    private int usesLeft;
    protected final Fighter owner;

    public Move(@Nullable Fighter owner, int maxMoves)
    {
        this.owner = owner;
        this.usesLeft = maxMoves;
    }

    /**
     * Used in conjunction with {@link #getChance()} to calculate whether this move hit
     * or missed, and of the result of {@link #getChance()} is actually used.
     *
     * @return {@code true} if the move can miss, {@code false} otherwise
     */
    public boolean canMiss() { return false; }

    /**
     * Used in conjunction with {@link #canMiss()} to calculate whether this move hit
     * or missed. Result is ignored when {@link #canMiss()} returns {@code false}
     *
     * @return the chance to hit on a scale from 0 to 1, where 0 = 0% chance and 1 = 100% chance etc.
     */
    public float getChance() { return 1.0f; }

    /**
     * The field {@link #usesLeft} is private to not make any illegal adjustments
     * to the amount of moves.
     *
     * @return the amount of uses left
     */
    public int getUsesLeft() { return usesLeft; }

    /**
     * This method is called every time a move is selected. The actual performing of the move
     * is handled in {@link #perform(Fighter[], Fighter[])}. This method calculates the success
     * of performing the move.
     *
     * @param ownParty Contains all the fighters in the party of the own player, excluding the owner
     * @param enemyParty Contains all the fighters in the party of the enemy, including the active fighter at index 0
     *
     * @return -1 if there were no uses left, 0 if the move missed and 1 if the move landed
     */
    public final int onPerform(Fighter[] ownParty, Fighter[] enemyParty)
    {
        if(usesLeft <= 0)
            return -1;

        boolean successful = true;

        if(canMiss())
            successful = new Random().nextFloat() <= getChance();

        if(successful)
            perform(ownParty, enemyParty);

        usesLeft--;
        return successful ? 0 : 1;
    }

    /**
     * Here, the actual move is performed. Only implement the mechanics of actually performing the
     * move, since determining if the move lands is calculated in {@linkplain #onPerform(FighterEntry[], FighterEntry[]) onPerform}.
     *
     * @see #onPerform(FighterEntry[], FighterEntry[])
     * @param ownParty contains every fighter in the player's party, excluding the owner.
     * @param enemyParty contains every fighter in the enemy's party, including the active fighter at index 0
     */
    public abstract void perform(Fighter[] ownParty, Fighter[] enemyParty);

    // Might not be perfect, but it's good enough for my
    // purposes... Oh, why am I not comparing the amount of
    // uses left? Because a fighter won't have the same move twice. (I hope)
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;

        return ((Move)o).owner.equals(owner);
    }
}
