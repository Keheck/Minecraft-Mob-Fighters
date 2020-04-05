package io.github.keheck.mobfighters.entity;

import io.github.keheck.mobfighters.MobFighters;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;

@ObjectHolder(MobFighters.MODID)
public class Entities
{
    public static final EntityType<MobBallEntity> mob_ball = null;

    public static final EntityType<Fighter> FIGHTER = EntityType.Builder
            .<Fighter>create(Fighter::new, EntityClassification.MISC)
            .size(1, 1)
            .build("fighter");

    public static void applyFighters()
    {

    }
}
