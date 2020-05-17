package io.github.keheck.mobfighters.fight.traits;

import java.util.Collections;
import java.util.Map;

/**
 * This interface marks any Traits requiring extra information.
 * You don't actually need to implement this interface. You can
 * create a static method reference like this: {@code ExampleTrait::getExtraInfo}.
 *
 * As for the "vanilla" traits, they all return a {@link Collections.UnmodifiableMap}.
 * This means that any attempt at modifying the maps (like adding or removing) returned by these methods
 * will result in a {@link UnsupportedOperationException}, so don't try to. Getting values, however, is fine.
 *
 * @see BatteryTrait#getExtraInfo()
 */
public interface INeedExtra
{
    Map<String, Class<?>> getExtraInfo();
}
