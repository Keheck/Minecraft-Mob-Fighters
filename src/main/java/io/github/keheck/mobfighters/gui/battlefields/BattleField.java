package io.github.keheck.mobfighters.gui.battlefields;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.keheck.mobfighters.MobFighters;
import io.github.keheck.mobfighters.util.helpers.MFMathHelper;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public final class BattleField
{
    protected final ResourceLocation MODEL_NAME;
    protected final FieldModel FIELD_MODEL;

    public BattleField(String modelName)
    {
        MODEL_NAME = new ResourceLocation(MobFighters.MODID, modelName);
        FIELD_MODEL = MobFighters.getInstance().modelManager.getFieldModel(MODEL_NAME);
    }

    private int xScale = 10;
    private int yScale = 10;
    private int zScale = 10;

    private float testRotation = 90;

    // TODO: Find a solution for blocks that render as "normal" items when wrapped in an ItemStack
    public void render(ItemRenderer renderer)
    {
        renderer.zLevel -= 150;
        RenderHelper.enableGUIStandardItemLighting();

        GlStateManager.pushMatrix();
        GlStateManager.scalef(1.1f, 1.1f, 1.1f);
        GlStateManager.rotatef(-30, 1, 0, 0);
        GlStateManager.rotatef(-20, 0, 1, 0);

        FIELD_MODEL.blocks.forEach((pos, block) ->
        {
            GlStateManager.pushMatrix();
            GlStateManager.translatef(pos.getX()*xScale, pos.getY()*yScale, pos.getZ()*zScale);
            resetBlockRotate();
            renderer.renderItemAndEffectIntoGUI(new ItemStack(block), 0, 0);
            GlStateManager.popMatrix();
        });

        GlStateManager.popMatrix();
        renderer.zLevel += 150;
    }

    /** De-Rotate blocks. */
    private static void resetBlockRotate()
    {
        GlStateManager.rotatef(225, 0, 1, 0);
        GlStateManager.rotatef(30, 1, 0, 0);
    }

    @SuppressWarnings({"SpellCheckingInspection", "unused", "ParameterCanBeLocal"})
    public void testRender(int posX, int posY, ItemRenderer renderer)
    {
        RenderHelper.enableGUIStandardItemLighting();
        posY = 30;

        GlStateManager.pushMatrix();
        GlStateManager.translatef(0, 0, 150);
        GlStateManager.rotatef(posX, 0, 1, 0);
        GlStateManager.rotatef(posY, 1, 0, 0);
        GlStateManager.translatef(0, 0, -150);
        GlStateManager.rotatef(testRotation, 0, 0, 1);
        renderer.renderItemAndEffectIntoGUI(new ItemStack(Items.DIAMOND_PICKAXE), posX, posY);
        renderer.renderItemAndEffectIntoGUI(new ItemStack(Items.BRICK_STAIRS), 0, 0);

        GlStateManager.translatef(0, 0, 150);
        GlStateManager.rotatef(posY, 1, 0, 0);
        GlStateManager.rotatef(posX, 0, 1, 0);
        GlStateManager.translatef(0, 0, -150);
        renderer.renderItemAndEffectIntoGUI(new ItemStack(Items.OAK_STAIRS), 0, 0);

        GlStateManager.translatef(0, 0, 150);
        GlStateManager.rotatef(posX, (30f/225f), 1, 0);
        GlStateManager.translatef(0, 0, -150);
        renderer.renderItemAndEffectIntoGUI(new ItemStack(Items.STONE_STAIRS), 0, 0);

        GlStateManager.translatef(0, 0, 150);
        GlStateManager.rotatef(posX, (225f/30f), 1, 0);
        GlStateManager.translatef(0, 0, -150);
        renderer.renderItemAndEffectIntoGUI(new ItemStack(Items.COBBLESTONE_STAIRS), 0, 0);

        /*GlStateManager.translatef(30, 30, 0);
        RenderHelper.enableGUIStandardItemLighting();

        FIELD_MODEL.blocks.forEach((pos, block) ->
        {
            if(block == Blocks.AIR)
                return;

            GlStateManager.pushMatrix();
            GlStateManager.rotated(i, 1, 1, 1);
            GlStateManager.translatef(pos.getX()*constScale, pos.getZ()*constScale, pos.getY()*constScale);
            renderer.renderItemAndEffectIntoGUI(new ItemStack(block), posX, posY);
            GlStateManager.popMatrix();
        });*/

        testRotation = MFMathHelper.cycleFloat(testRotation, 0, 360, 2);
        GlStateManager.popMatrix();

    }

    @Override
    public String toString() { return "BattleField#" + MODEL_NAME; }
}
