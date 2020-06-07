package io.github.keheck.mobfighters;

import io.github.keheck.mobfighters.registry.ClientRegistry;
import io.github.keheck.mobfighters.gui.battlefields.FieldModelManager;
import io.github.keheck.mobfighters.util.network.FightStartPacket;
import io.github.keheck.mobfighters.util.network.MobfightersNetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.IndexedMessageCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MobFighters.MODID)
public final class MobFighters
{
    // TODO: Document that shit!!!
    // TODO: Finish drawing the GUIs

    public static final String MODID = "mobfighters";
    public static final String MC = "minecraft";

    public static final Logger LOGGER = LogManager.getLogger("Mob Fighters");
    private static MobFighters instance;

    public FieldModelManager modelManager;
    public MobFighters()
    {
        instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doCommonSetup);

        // If this mod is on a dedicated server, don't run this bit of code
        // Is there a better way to go about doing this?
        if(!"SERVER".equals(Thread.currentThread().getThreadGroup().getName()))
        {
            // This is not in doClientSetup() because then the FieldModelManager.apply() method doesn't get called.
            // I don't want to manually reload Minecraft resources, there has to be a better way
            modelManager = new FieldModelManager();
            ((SimpleReloadableResourceManager)Minecraft.getInstance().getResourceManager()).addReloadListener(modelManager);
        }
    }

    public void doClientSetup(FMLClientSetupEvent event)
    {
        ClientRegistry.registerRenderers();
    }


    private static int MSG_ID = 0;
    public void doCommonSetup(FMLCommonSetupEvent event)
    {
        MobfightersNetworkHandler.CHANNEL.registerMessage(++MSG_ID, FightStartPacket.class, FightStartPacket::encode, FightStartPacket::decode, FightStartPacket::handle);
    }

    public static MobFighters getInstance() { return instance; }
}
