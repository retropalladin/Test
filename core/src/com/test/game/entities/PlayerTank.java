package com.test.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.test.game.Level;
import com.test.game.utils.Enums;

public class PlayerTank extends NpcTank {

    public boolean alive;
    public Enums.TankType type;
    public Body body;
    private Level level;

    public PlayerTank(Level level, Body body) {
        this.level = level;
        this.body = body;
        alive = true;
    }
}
