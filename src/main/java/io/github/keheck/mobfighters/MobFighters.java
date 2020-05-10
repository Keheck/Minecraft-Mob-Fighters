package io.github.keheck.mobfighters;

import io.github.keheck.mobfighters.registry.ClientRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MobFighters.MODID)
public final class MobFighters
{
    public static final String MODID = "mobfighters";
    public static final Logger LOGGER = LogManager.getLogger("Mob Fighters");
    private static MobFighters instance;
    public static final boolean testing = true;

    public MobFighters()
    {
        instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClientStuff);
    }

    public void setupClientStuff(FMLClientSetupEvent event) { ClientRegistry.registerRenderers(); }

    public static ResourceLocation getModLocation(String forResource) { return new ResourceLocation(MODID, forResource); }

    public static MobFighters getInstance() { return instance; }
}
