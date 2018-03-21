package com.test.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class Tank implements Pool.Poolable {

    public boolean alive;
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
