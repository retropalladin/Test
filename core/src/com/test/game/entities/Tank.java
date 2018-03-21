package com.test.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.test.game.utils.Enums.TankType;

public class Tank implements Pool.Poolable {

    public boolean alive;
    public TankType type;
    public Body body;

    public void init(Body body) {
        this.body = body;
        alive = true;
    }

    @Override
    public void reset() {
        body = null;
        alive = false;
    }
}
