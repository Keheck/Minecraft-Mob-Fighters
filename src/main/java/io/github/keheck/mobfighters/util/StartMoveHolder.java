package io.github.keheck.mobfighters.util;

import io.github.keheck.mobfighters.registry.entries.MoveEntry;

public class StartMoveHolder
{
    private final MoveEntry entry;
    private final float probability;

    public StartMoveHolder(MoveEntry entry, float probability)
    {
        this.entry = entry;
        this.probability = probability;
    }

    public static int sort(StartMoveHolder h1, StartMoveHolder h2) { return Float.compare(h2.probability, h1.probability); }

    public MoveEntry getEntry() { return entry; }

    public float getProbability() { return probability; }
}
