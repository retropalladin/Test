package com.test.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.test.game.Level;
import com.test.game.utils.Enums.TankType;

public class NpcTank implements Pool.Poolable {
    public boolean alive;
    public TankType type;
    public Body body;
    private Level level;

    public void init(Level level, Body body) {
        this.level = level;
        this.body = body;
        alive = true;
    }

    @Override
    public void reset() {
        body = null;
        level = null;
        alive = false;
    }
}
