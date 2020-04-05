package io.github.keheck.mobfighters.registry;

import io.github.keheck.mobfighters.entity.MobBallEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class ClientRegistry
{
    public static void registerRenderers()
    {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        RenderingRegistry.registerEntityRenderingHandler(MobBallEntity.class, manager -> new SpriteRenderer<>(manager, renderer));
    }
}
