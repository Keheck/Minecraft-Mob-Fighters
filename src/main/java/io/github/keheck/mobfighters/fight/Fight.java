package io.github.keheck.mobfighters.fight;

import io.github.keheck.mobfighters.entity.AbstractTrainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class Fight
{
    private final FightType type;
    private final PlayerEntity player;
    private final LivingEntity enemy;

    /**
     * Is called when one player has accepted the challenge of another player.
     *
     * @param player1 the challenger.
     * @param player2 the challenged.
     */
    public Fight(PlayerEntity player1, PlayerEntity player2)
    {
        this.player = player1;
        this.enemy = player2;
        this.type = FightType.PLAYER;
    }

    /**
     * Is called when a {@linkplain AbstractTrainer Trainer} entity meets a player.
     *
     * @param player The player the trainer challenged.
     * @param trainer The trainer that challenged the trainer.
     */
    public Fight(PlayerEntity player, AbstractTrainer trainer)
    {
        this.player = player;
        this.enemy = trainer;
        this.type = FightType.TRAINER;
    }

    // TODO: Change the javadoc according to the mechanic
    /**
     * Is called when a entity was challenged for a fight.
     *
     * @param player The player that fights the mob.
     * @param mob The mob the player fights.
     */
    public Fight(PlayerEntity player, LivingEntity mob)
    {
        this.player = player;
        this.enemy = mob;
        this.type = FightType.WILD;
    }

    /**
     * Indicates which enemy {@link #player} faces. This is to determine what
     * action to take when another ends, and what action can be done (e.g. can the player throw a mob ball).
     * <ul>
     *     <li>WILD:    Indicates that the player is fighting against a single mob without a trainer</li>
     *     <li>TRAINER: Indicated that the player is fighting against a {@linkplain AbstractTrainer Trainer}</li>
     *     <li>PLAYER:  Indicates that the player is playing against another player</li>
     * </ul>
     */
    public enum FightType
    {
        WILD, TRAINER, PLAYER
    }
}
