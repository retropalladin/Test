package com.test.game.utils;


public class Enums {

    // GameplayScreen mech

    public enum  GameplayScreenState{
        RUN,
        INVENTORY_PAUSE,
    }

    // Level mech
    public enum TankMoveState {
        WAITING,
        ON_MOVE,
        ROTATING
    }

    public enum TankShootState {
        READY,
        RELOADING
    }

    public enum TankType {
        LIGHT_TANK,
        HEAVY_TANK
    }

    public enum AmmoType {
        NORMAL_BULLET,
        PLASMA_BULLET,
        DOUBLE_NORMAL_BULLET,
        DOUBLE_PLASMA_BULLET,
        AP_BULLET,
        RAP_BULLET,
        ENERGY_DRINK,
        TIME_STOP
    }

    public enum  WallType {
        WOODEN_WALL,
        STONE_WALL,
        LEVEL_BORDER
    }

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
