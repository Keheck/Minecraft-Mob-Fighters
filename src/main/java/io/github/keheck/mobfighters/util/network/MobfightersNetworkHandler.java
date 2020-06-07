package io.github.keheck.mobfighters.util.network;

import io.github.keheck.mobfighters.MobFighters;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MobfightersNetworkHandler
{
    private static final String VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MobFighters.MODID, "main"),
            () -> VERSION,
            VERSION::equals,
            VERSION::equals);
}
