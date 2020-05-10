package io.github.keheck.mobfighters.entity;

import io.github.keheck.mobfighters.MobFighters;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ObjectHolder(MobFighters.MODID)
public class Entities
{
    private static final ArrayList<Map.Entry<ResourceLocation, EntityType<?>>> ENTITY_ENTRIES = new ArrayList<>();

    public static final EntityType<MobBallEntity> mob_ball = null;

    public static void applyFighters()
    {

    }

    public static boolean hasFighterEquivalent(Entity entity)
    {
        return true;
    }
}
