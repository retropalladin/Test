package com.test.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.test.game.utils.Enums;

public class Bullet implements Pool.Poolable {
    public boolean alive;
    public Enums.BulletType type;
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
