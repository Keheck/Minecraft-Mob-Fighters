package io.github.keheck.mobfighters.fighter.traits;

public abstract class Trait
{
    private TraitType[] types;
    private String name;

    public Trait(String name, TraitType... traitTypes)
    {
        types = traitTypes.length == 0 ? new TraitType[]{TraitType.PASSIVE} : traitTypes;
        this.name = name;
    }

    public String getName() { return name; }

    public abstract void onPassive();

    public abstract void onPreFight();

    public abstract void onPostFight();

    public abstract void onDeath();

    public abstract void onStandBy();

    /**
     * PASSIVE: Gets activated at all times
     * PRE_FIGHT: Gets activated when the Fighter enters the battle.
     * POST_FIGHT: Gets activated when the Fighter leaves the battle. (NOT when it dies, see ON_DEATH)
     * ON_DEATH: Gets activated when the Fighter dies.
     * STANDBY: Gets activated the the Fighter's move starts, but before any action may be taken.
     */
    public enum TraitType
    {
        PASSIVE("passive"),
        PRE_FIGHT("pre-fight"),
        POST_FIGHT("post-fight"),
        ON_DEATH("when died"),
        STANDBY("on standby");

        private String displayName;

        TraitType(String displayName) { this.displayName = displayName; }

        @Override
        public String toString() { return displayName; }
    }
}
