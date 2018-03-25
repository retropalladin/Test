package com.test.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.test.game.Level;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankType;
import com.test.game.utils.MaterialEntity;

public class NpcTank extends MaterialEntity implements Pool.Poolable {

    public Direction direction;
    public TankType type;
    public int x;
    public int y;
    protected Level level;

    public void init(Level level, Body body) {
        this.level = level;
        this.setAlive(true);
        this.setBody(body);
    }

    public void configureNpcTankType(short category, TankType type, Direction direction) {
        this.direction = direction;
        this.setCategory(category);
        this.type = type;
    }


    @Override
    public void reset() {
        this.setAlive(false);
        this.setBody(null);
        this.setCategory((short) 0);
        this.setHp(0);

    }
}
