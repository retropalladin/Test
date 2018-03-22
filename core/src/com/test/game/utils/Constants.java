package com.test.game.utils;

import com.badlogic.gdx.math.Vector2;

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
    public static final float BULLET_HEIGHT = 0.5f;
    public static final float BULLET_WIDTH_H = BULLET_WIDTH * 0.5f;
    public static final float BULLET_HEIGHT_H = BULLET_HEIGHT * 0.5f;
    public static final float BULLET_EPS_SPAWN = 0.1f;
    public static final float DOUBLE_BULLET_EPS_SPAWN = 0.1f;
    public static final float DOUBLE_BULLET_EPS_SPAWN_H = DOUBLE_BULLET_EPS_SPAWN * 0.5f;

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

    public static final short CATEGORY_WALL = 1;
    public static final short CATEGORY_ALLY_TANK = (1 << 1);
    public static final short CATEGORY_ENEMY_TANK = (1 << 2);
    public static final short CATEGORY_ALLY_BULLET = (1 << 3);
    public static final short CATEGORY_ENEMY_BULLET = (1 << 4);

    public static final short MASK_WALL = CATEGORY_ALLY_TANK | CATEGORY_ENEMY_TANK | CATEGORY_ALLY_BULLET | CATEGORY_ENEMY_BULLET;
    public static final short MASK_ALLY_TANK = CATEGORY_WALL | CATEGORY_ALLY_TANK | CATEGORY_ENEMY_TANK | CATEGORY_ENEMY_BULLET;
    public static final short MASK_ENEMY_TANK = CATEGORY_WALL | CATEGORY_ALLY_TANK | CATEGORY_ENEMY_TANK | CATEGORY_ALLY_BULLET;
    public static final short MASK_ALLY_BULLET = CATEGORY_WALL | CATEGORY_ENEMY_TANK | CATEGORY_ENEMY_BULLET;
    public static final short MASK_ENEMY_BULLET = CATEGORY_WALL | CATEGORY_ALLY_TANK | CATEGORY_ALLY_BULLET;
}
