package io.github.keheck.mobfighters.gui.screen;

import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;

import static io.github.keheck.mobfighters.util.Constants.*;

public final class FightScreen extends Screen
{
    private int fight_state = INIT;

    public FightScreen()
    {
        super(NarratorChatListener.field_216868_a);
    }

    @Override
    protected void init()
    {

    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {

    }

    private void attackButtonPressed(Button button)
    {

    }

    private void itemButtonPressed(Button button)
    {

    }

    private void attackPerformed(Button button)
    {

    }

    @Override
    public boolean shouldCloseOnEsc() { return false; }
}
