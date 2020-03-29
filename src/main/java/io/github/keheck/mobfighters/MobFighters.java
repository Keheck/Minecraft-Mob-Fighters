package io.github.keheck.mobfighters;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MobFighters.MODID)
public class MobFighters
{
    public static final String MODID = "mobfighters";
    public static final Logger LOGGER = LogManager.getLogger("Mob Fighters");
    private static MobFighters instance;

    public MobFighters()
    {
        instance = this;
    }

    public static MobFighters getInstance() { return instance; }
}
