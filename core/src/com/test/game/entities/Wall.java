package com.test.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.test.game.Level;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.WallType;
import com.test.game.utils.MaterialEntity;

public class Wall extends MaterialEntity implements Pool.Poolable {

    ///////////////////////////////////////////
    /// Constants Settings                  ///
    ///////////////////////////////////////////

    public static int STONE_WALL_HP_MAX = 1000;
    public static int WOODEN_WALL_HP_MAX = 4;

    ///////////////////////////////////////////
    /// Constants Physics                   ///
    ///////////////////////////////////////////

    public static final float WALL_FRICTION = 0f;
    public static final float WALL_RESTITUTION = 0f;

    ///////////////////////////////////////////
    /// END CONSTANTS                       ///
    ///////////////////////////////////////////

    public boolean immortal;
    public WallType type;

    private Level level;

    public void init(Level level, Body body) {
        this.level = level;
        this.setAlive(true);
        this.setBody(body);
        this.setGridCoordinates((short)-1, (short)-1);
        immortal = true;
    }

    public void configureWallType(short category, WallType type){
        this.category = category;
        this.type = type;
        switch (type){
            case WOODEN_WALL:
                hp = WOODEN_WALL_HP_MAX;
                immortal = false;
                break;
            case STONE_WALL:
                hp = STONE_WALL_HP_MAX;
                immortal = false;
                break;
            case LEVEL_BORDER:
                hp = Integer.MAX_VALUE;
                immortal = true;
        }
    }

    public void takeDamage(int damage){
        if(immortal)
            decreaseHp(0);
        else{
            if(!decreaseHp(damage)){
                level.objectsMatrix[gridY][gridX] = Constants.Physics.CATEGORY_EMPTY;
            }
        }
    }

    @Override
    public void reset() {
        this.level = null;
        this.setAlive(false);
        this.setBody(null);
        this.setCategory((short) 0);
        this.setHp(0);
        immortal = false;
    }
}
