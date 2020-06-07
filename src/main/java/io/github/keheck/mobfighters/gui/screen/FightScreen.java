package io.github.keheck.mobfighters.gui.screen;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import io.github.keheck.mobfighters.MobFighters;
import io.github.keheck.mobfighters.gui.battlefields.BattleField;
import io.github.keheck.mobfighters.util.network.FightStartPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public final class FightScreen extends Screen
{
    private static final ResourceLocation FIELD_SCREEN = new ResourceLocation(MobFighters.MODID, "textures/gui/fight/main.png");
    private static final ResourceLocation ATTACK_SCREEN = new ResourceLocation(MobFighters.MODID, "textures/gui/fight/moves.png");
    private static final ResourceLocation ITEMS_SCREEN = new ResourceLocation(MobFighters.MODID, "textures/gui/fight/items.png");
    private static final ResourceLocation MOBS_SCREEN = new ResourceLocation(MobFighters.MODID, "textures/gui/fight/party.png");

    // Screens are at max 480x254

    // 0 = main screen, 1 = moves, 2 = items, 3 = mobs
    private GuiState guiState = GuiState.MAIN;
    private final World world;
    private LivingEntity entity;
    private LivingEntity entity2;
    private BattleField field;
    private int guiLeft;
    private int guiTop;
    private int sizeX = 197;
    private int sizeY = 189;

    private Button backButton;
    private Button itemButton;
    private Button attackButton;
    private Button mobsButton;

    private Button[] attacks = new Button[4];

    public FightScreen(World world)
    {
        super(NarratorChatListener.field_216868_a);
        this.world = world;
    }

    @Override
    // TODO: Finish setting up the GUI.
    protected void init()
    {
        super.init();
        this.guiLeft = (this.width - this.sizeX) / 2;
        this.guiTop = (this.height - this.sizeY) / 2;
        entity = EntityType.ZOMBIE.create(world);
        entity2 = EntityType.CREEPER.create(world);
        field = new BattleField("letter_f");

        backButton   = this.addButton(new Button(10,  10,  50,  20, "Back",   this::pressedBack   ));
        itemButton   = this.addButton(new Button(20,  170, 100, 20, "Item",   this::pressedItems  ));
        attackButton = this.addButton(new Button(20,  200, 100, 20, "Attack", this::pressedAttacks));
        mobsButton   = this.addButton(new Button(350, 170, 100, 20, "Mobs",   this::pressedMobs   ));

        for(int i = 0; i < attacks.length; i++)
        {
            final int iFin = i;

            Button button = new Button(i%2 == 0 ? 20 : 350, i <= 1 ? 170 : 200, 100, 20, "", b -> selectAttack(b, iFin));
            attacks[i] = button;
            this.addButton(button);
        }

        switchButtonStates();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translatef(0, 0, -50);
        renderBackground();
        TextureManager manager = Minecraft.getInstance().getTextureManager();

        switch(guiState)
        {
            case MAIN:
                manager.bindTexture(FIELD_SCREEN);
                blit(guiLeft, guiTop, 0, 0, 197, 189);
                GlStateManager.pushMatrix();
                GlStateManager.translatef(240, 147, 100);
                field.render(this.itemRenderer);
                //drawEntityOnScreen(entity);
                GlStateManager.popMatrix();
                break;
            case ATTACKS:
                GlStateManager.color4f(1, 1, 1, 1);
                manager.bindTexture(ATTACK_SCREEN);
                // whereX, whereY, texStartX, textStartY, texWidth, texHeight
                blit(guiLeft, guiTop, 0, 0, sizeX, sizeY);
                break;
            // TODO: Complete the item setup
            case ITEMS:
                GlStateManager.color4f(1, 1, 1, 1);
                manager.bindTexture(ITEMS_SCREEN);
                blit(guiLeft, guiTop, 0, 0, sizeX, sizeY);
                break;
            case MOBS:
                break;
        }

        super.render(mouseX, mouseY, partialTicks);
        GlStateManager.popMatrix();
    }

    @Override
    public void renderBackground() { super.renderBackground(); }

    private float rotAngle = 0;

    private void drawEntityOnScreen(LivingEntity ent)
    {
        GlStateManager.enableColorMaterial();
        RenderHelper.enableStandardItemLighting();
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getRenderManager();
        entityrenderermanager.setRenderShadow(false);
        entityrenderermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        entityrenderermanager.setRenderShadow(true);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.activeTexture(GLX.GL_TEXTURE1);
        GlStateManager.disableTexture();
        GlStateManager.activeTexture(GLX.GL_TEXTURE0);
    }

    // TODO: Setup the actions of the buttons.
    private void pressedAttacks(Button button)
    {
        guiState = GuiState.ATTACKS;
        sizeX = 0;
        sizeY = 0;

        for(Button b : attacks) b.visible = true;

        switchButtonStates();
    }

    private void pressedItems(Button button)
    {
        guiState = GuiState.ITEMS;
        sizeX = 141;
        sizeY = 156;

        switchButtonStates();
    }

    private void pressedMobs(Button button)
    {
        guiState = GuiState.MOBS;
        sizeX = 0;
        sizeY = 0;

        switchButtonStates();
    }

    private void pressedBack(Button button)
    {
        guiState = GuiState.MAIN;
        sizeX = 197;
        sizeY = 189;

        switchButtonStates();
    }

    private void selectAttack(Button button, int index)
    {

    }

    private void switchButtonStates()
    {
        guiLeft = (this.width - this.sizeX) / 2;
        guiTop = (this.height - this.sizeY) / 2;

        attackButton.visible = guiState == GuiState.MAIN;
        mobsButton.visible = guiState == GuiState.MAIN;
        itemButton.visible = guiState == GuiState.MAIN;
        backButton.visible = guiState != GuiState.MAIN;
        for(Button b : attacks) b.visible = guiState == GuiState.ATTACKS;
    }

    @Override
    public boolean shouldCloseOnEsc() { return true; }

    private enum GuiState
    {
        MAIN, ATTACKS, ITEMS, MOBS
    }
}
