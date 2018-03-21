package com.test.game.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Владимир on 20.03.2018.
 */

public class Constants {
    // developing
    public static final boolean DEBUG = true;
    public static final boolean DEBUG_PHYSICS_RENDER = true;

    // Level settings
    public static final float FRAME_TIME_MAX = 0.25f;

    // LevelRenderer settings
    public static final int WORLD_VISIBLE_HEIGHT = 32;

    // Physics settings
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;
    public static final float PHYSICS_STEP = 1/45f;

    public static final float CELL_SIZE = 2;
    public static final float BULLET_WIDTH = 1;
    public static final float BULLET_HEIHT = 0.5f;
    public static final float BULLET_EPS_SPAWN = 0.05f;

    public static final float LIGHT_TANK_DENSITY = 0.3f;
    public static final float HEAVY_TANK_DENSITY = 0.6f;
    public static final float TANK_FRICTION = 0f;
    public static final float TANK_RESTITUTION = 0f;

    public static final float NORMAL_BULLET_DENSITY = 0.7f;
    public static final float PLASMA_BULLET_DENSITY = 0.5f;
    public static final float AP_BULLET_DENSITY = 0.3f;
    public static final float BULLET_FRICTION = 0f;
    public static final float BULLET_RESTITUTION = 0f;

    public static final float BULLET_IMPULSE = 2.0f;
    public static final Vector2 BULLET_UP_IMPULSE = new Vector2(0,BULLET_IMPULSE);
    public static final Vector2 BULLET_DOWN_IMPULSE = new Vector2(0,-BULLET_IMPULSE);
    public static final Vector2 BULLET_RIGHT_IMPULSE = new Vector2(BULLET_IMPULSE,0);
    public static final Vector2 BULLET_LEFT_IMPULSE = new Vector2(-BULLET_IMPULSE,0);


    public static final int CATEGORY_BITS_ALLY = 0x0001;
    public static final int CATEGORY_BITS_ENEMY = 0x0002;

    public static final int GROUP_TANKS = 1;
    public static final int GROUP_BULLETS = 2;
}
