package com.test.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.test.game.utils.Enums;

public class Wall implements Pool.Poolable {

    public boolean alive;
    public boolean immortal;
    public int hp;

    public Enums.WallType type;
    public Body body;

    public void init(Body body) {
        this.body = body;
        alive = true;
        immortal = true;
    }

    @Override
    public void reset() {
        body = null;
        hp = 0;
        alive = false;
        immortal = false;
    }
}
