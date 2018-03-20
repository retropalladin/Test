package com.test.game.utils;

/**
 * Created by Владимир on 20.03.2018.
 */

public class Constants {
    // developing
    public static final boolean DEBUG = true;
    public static final boolean DEBUG_PHYSICS_RENDER = true;

    // Level settings
    public static final int WORLD_SIZE = 20;
    public static final float FRAME_TIME_MAX = 0.25f;

    // Physics settings
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;
    public static final float PHYSICS_STEP = 1/45f;
}
