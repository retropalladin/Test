package com.test.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums;
import com.test.game.utils.Enums.WallType;
import com.test.game.utils.MaterialEntity;

public class Wall extends MaterialEntity implements Pool.Poolable {

    public boolean immortal;
    public WallType type;

    public void init(Body body) {
        this.setAlive(true);
        this.setBody(body);
        immortal = true;
    }

    public void configureWallType(short category, WallType type){
        this.category = category;
        this.type = type;
        switch (type){
            case WOODEN_WALL:
                hp = Constants.WOODEN_WALL_HP_MAX;
                immortal = false;
                break;
            case STONE_WALL:
                hp = Constants.STONE_WALL_HP_MAX;
                immortal = false;
                break;
            case BUSH_WALL:
                hp = Constants.BUSH_WALL_HP_MAX;
                immortal = false;
                break;
        }
    }

    @Override
    public void reset() {
        this.setAlive(false);
        this.setBody(null);
        this.setCategory((short) 0);
        this.setHp(0);
        immortal = false;
    }
}
