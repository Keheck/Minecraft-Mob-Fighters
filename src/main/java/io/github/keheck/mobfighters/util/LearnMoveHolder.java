package io.github.keheck.mobfighters.util;

import io.github.keheck.mobfighters.registry.entries.MoveEntry;

public class LearnMoveHolder
{
    private final MoveEntry entry;
    private final float probability;
    private final int level;

    public LearnMoveHolder(MoveEntry entry, float probability, int level)
    {
        this.entry = entry;
        this.probability = probability;
        this.level = level;
    }

    public static int sort(LearnMoveHolder l1, LearnMoveHolder l2) { return Float.compare(l2.probability, l1.probability); }

    public MoveEntry getEntry() { return entry; }

    public int getLevel() { return level; }

    public float getProbability() { return probability; }
}
