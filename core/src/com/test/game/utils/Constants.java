package com.test.game.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {
    // developing
    public static final boolean DEBUG = true;
    public static final boolean DEBUG_PHYSICS_RENDER = true;

    // Level settings
    public static final float FRAME_TIME_MAX = 0.25f;

    // LevelRenderer settings
    public static final int CHASE_CAMERA_HORIZONTAL_CELLS_SHIFT = 2;
    public static final int CHASE_CAMERA_VERTICAL_CELLS_SHIFT = 2;
    public static final float WORLD_VISIBLE_HEIGHT = 24;
    public static final float CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED = 5f;
    public static final float CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED = 10.0f;

    // Physics settings
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;
    public static final float PHYSICS_STEP = 1/60f;
    public static final float CELL_SIZE = 2;
    public static final float CELL_SIZE_H = CELL_SIZE * 0.5f;

    public static final float WALL_FRICTION = 0f;
    public static final float WALL_RESTITUTION = 0f;

    public static final float LIGHT_TANK_DENSITY = 0.3f;
    public static final float HEAVY_TANK_DENSITY = 0.6f;
    public static final float TANK_FRICTION = 0f;
    public static final float TANK_RESTITUTION = 0f;

    public static final float NORMAL_BULLET_DENSITY = 0.7f;
    public static final float PLASMA_BULLET_DENSITY = 0.5f;
    public static final float AP_BULLET_DENSITY = 0.3f; // RAP_BULLET_DENSITY is equal
    public static final float BULLET_FRICTION = 0f;
    public static final float BULLET_RESTITUTION = 0f;

    public static final float BULLET_IMPULSE = 0.0f;
    public static final Vector2 BULLET_UP_IMPULSE = new Vector2(0,BULLET_IMPULSE);
    public static final Vector2 BULLET_DOWN_IMPULSE = new Vector2(0,-BULLET_IMPULSE);
    public static final Vector2 BULLET_RIGHT_IMPULSE = new Vector2(BULLET_IMPULSE,0);
    public static final Vector2 BULLET_LEFT_IMPULSE = new Vector2(-BULLET_IMPULSE,0);

    public static final float TANK_IMPULSE = 4.0f;
    public static final float TANK_MOVE_CATCH_EPS = 0.05f;
    public static final Vector2 TANK_UP_IMPULSE = new Vector2(0,TANK_IMPULSE);
    public static final Vector2 TANK_DOWN_IMPULSE = new Vector2(0,-TANK_IMPULSE);
    public static final Vector2 TANK_RIGHT_IMPULSE = new Vector2(TANK_IMPULSE,0);
    public static final Vector2 TANK_LEFT_IMPULSE = new Vector2(-TANK_IMPULSE,0);

    public static final short CATEGORY_EMPTY = 1;
    public static final short CATEGORY_WALL = (1 << 2);
    public static final short CATEGORY_ALLY_TANK = (1 << 3);
    public static final short CATEGORY_ENEMY_TANK = (1 << 4);
    public static final short CATEGORY_TANK_ON_MOVE = (1 << 5);
    public static final short CATEGORY_ALLY_BULLET = (1 << 13);
    public static final short CATEGORY_ENEMY_BULLET = (1 << 14);

    public static final short MASK_WALL = CATEGORY_ALLY_TANK | CATEGORY_ENEMY_TANK | CATEGORY_ALLY_BULLET | CATEGORY_ENEMY_BULLET;
    public static final short MASK_ALLY_TANK = CATEGORY_WALL | CATEGORY_ALLY_TANK | CATEGORY_ENEMY_TANK | CATEGORY_ENEMY_BULLET;
    public static final short MASK_ENEMY_TANK = CATEGORY_WALL | CATEGORY_ALLY_TANK | CATEGORY_ENEMY_TANK | CATEGORY_ALLY_BULLET;
    public static final short MASK_ALLY_BULLET = CATEGORY_WALL | CATEGORY_ENEMY_TANK | CATEGORY_ENEMY_BULLET;
    public static final short MASK_ENEMY_BULLET = CATEGORY_WALL | CATEGORY_ALLY_TANK | CATEGORY_ALLY_BULLET;

    public static final short PLAYER_TANK_MOVE_MASK = CATEGORY_EMPTY;
    // Wall settings
    public static int STONE_WALL_HP_MAX = 1000;
    public static int WOODEN_WALL_HP_MAX = 4;
    public static int BUSH_WALL_HP_MAX = 1;
    public static float BUSH_WALL_RADIUS = 0.5f;

    // Tank settings
    public static final float TANK_WIDTH = CELL_SIZE * 0.8f;
    public static final float TANK_WIDTH_H = TANK_WIDTH * 0.5f;
    public static final float TANK_HEIGHT = CELL_SIZE * 0.8f;
    public static final float TANK_HEIGHT_H = TANK_HEIGHT * 0.5f;
    public static final float TANK_MARGIN = CELL_SIZE_H - TANK_HEIGHT_H;
    // Bullet settings

    public static final int NORMAL_BULLET_MAX_HP = 1;
    public static final int PLASMA_BULLET_MAX_HP = 1;
    public static final int AP_BULLET_MAX_HP = 2;
    public static final int RAP_BULLET_MAX_HP = 2;

    public static final float BULLET_WIDTH = 0.7f;
    public static final float BULLET_HEIGHT = 0.35f;
    public static final float BULLET_WIDTH_H = BULLET_WIDTH * 0.5f;
    public static final float BULLET_HEIGHT_H = BULLET_HEIGHT * 0.5f;

    public static final float BULLET_EPS_SPAWN = 0.1f;
    public static final float DOUBLE_BULLET_EPS_SPAWN = 0.2f;
    public static final float DOUBLE_BULLET_EPS_SPAWN_H = DOUBLE_BULLET_EPS_SPAWN * 0.5f;
}
