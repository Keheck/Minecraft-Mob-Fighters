package io.github.keheck.mobfighters.entity;

import io.github.keheck.mobfighters.MobFighters;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;

public class Entities
{
    public static final EntityType<MobBallEntity> MOB_BALL = EntityType.Builder
            .<MobBallEntity>create(MobBallEntity::new, EntityClassification.MISC)
            .size(.25f, .25f)
            .build("mob_ball");

    public static final EntityType<Fighter> FIGHTER = EntityType.Builder
            .<Fighter>create(Fighter::new, EntityClassification.MISC)
            .size(1, 1)
            .build("fighter");

    public static void applyFighters()
    {

    }
}
