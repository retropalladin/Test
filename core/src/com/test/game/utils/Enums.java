package com.test.game.utils;


public class Enums {

    public enum TankState {
        WAITING, ON_MOVE, ROTATING
    }

    public enum TankType {
        LIGHT_TANK, HEAVY_TANK
    }

    public enum BulletType {
        NORMAL_BULLET, PLASMA_BULLET, AP_BULLET, RAP_BULLET
    }

    public enum DoubleBulletType {
        DOUBLE_NORMAL_BULLET, DOUBLE_PLASMA_BULLET
    }

    public enum  WallType {
        WOODEN_WALL, STONE_WALL, BUSH_WALL
    }

    public enum Direction {
        LEFT , RIGHT, UP, DOWN
    }
}
