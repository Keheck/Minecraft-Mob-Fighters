package io.github.keheck.mobfighters.fight.traits;

import io.github.keheck.mobfighters.fight.fighters.Fighter;
import io.github.keheck.mobfighters.registry.entries.FighterEntry;
import io.github.keheck.mobfighters.registry.entries.TraitEntry;
import io.github.keheck.mobfighters.util.ITraitFactory;
import net.minecraft.resources.IReloadableResourceManager;

public abstract class Trait
{
    protected final Fighter owner;
    protected final ActivationType[] type;

    // I advice against modifying this array, it most likely will break your trait.
    protected Object[] extraInfo;

    public Trait(Fighter owner, ActivationType... type)
    {
        this.owner = owner;
        this.type = type;
    }

    /**
     * Only important if this trait requires extra information.
     * If your trait uses extra data, please be sure on what order
     * the objects are going to be stored in (It will most likely
     * be the order that {@link INeedExtra#getExtraInfo()} returns
     * it's values in).
     *
     * @see TraitEntry#getExtra()
     * @see TraitEntry#TraitEntry(ITraitFactory, INeedExtra)
     * @see FighterEntry#gatherData(IReloadableResourceManager)
     */
    public final void applyExtraInfo(Object... extraInfo) { this.extraInfo = extraInfo; }

    /**
     * This method is called only at specific moments during a fight as specified by the {@link #type} field
     *
     * @param ownParty contains every fighter in the player's party, excluding the owner.
     * @param enemyParty contains every fighter in the enemy's party, including the active fighter at index 0
     * @param phase Indicates what is currently going on. Can be ignored if the trait only activates at one phase.
     */
    public abstract void applyEffect(Fighter[] ownParty, Fighter[] enemyParty, ActivationType phase);

    /**
     *  This enum denotes the moment at which the specific trait activates.
     *  The effects are as follows: <br/>
     *  {@link #NO_EFFECT}: This trait does nothing during the fight. Use it as a marker for other traits. <br/><br/>
     *  {@link #INIT}: This trait activates only once, in fact before the first fighters of the fight are deployed.
     *                 Use it for, well, idk, I don't use it...<br/><br/>
     *  {@link #STANDBY}: This trait activates after a round ends, but before the next actions can be taken.
     *                    Use it for stuff like countdown traits or traits depending on weather or biomes.<br/><br/>
     *  {@link #STATUS}: This trait activates once a status effect is applied. Use it to apply resistances.<br/><br/>
     *  {@link #MOVE}: This trait activates once the owner performs a move. Use it for stuff like damage or hit-chance
     *                 modification.<br/><br/>
     *  {@link #ENEM_MOVE}: Like the {@link #MOVE} trait, with the difference being that it activates when the #
     *                      enemy fighter performs a move.
     *
     */
    public enum ActivationType
    {
        NO_EFFECT,
        INIT,
        STANDBY,
        STATUS,
        MOVE,
        ENEM_MOVE
    }
}
