package io.github.keheck.mobfighters.util.helpers;

import io.github.keheck.mobfighters.gui.screen.FightScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class GUIDisplayHelper
{
    public static void displayGuiScreen(ScreenType screen, Object... extra)
    {
        Minecraft mc = Minecraft.getInstance();

        switch(screen)
        {
            case FIGHT:
                mc.displayGuiScreen(new FightScreen((World)extra[0]));
                break;
            case BOX:

        }
    }

    public enum ScreenType
    {
        FIGHT, BOX
    }
}
