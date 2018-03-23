package com.test.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.test.game.Level;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankType;

public class PlayerTank extends NpcTank {

    public boolean alive;
    public Direction direction;
    public TankType type;
    public Body body;
    private Level level;

    public PlayerTank(Level level, Body body) {
        this.level = level;
        this.body = body;
        alive = true;
    }

    public void update()
    {
        // Demo
        body.setLinearVelocity(Vector2.Zero);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            body.applyLinearImpulse(Constants.TANK_LEFT_IMPULSE,body.getWorldCenter(),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.applyLinearImpulse(Constants.TANK_RIGHT_IMPULSE,body.getWorldCenter(),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            body.applyLinearImpulse(Constants.TANK_UP_IMPULSE,body.getWorldCenter(),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            body.applyLinearImpulse(Constants.TANK_DOWN_IMPULSE,body.getWorldCenter(),true);
        }
    }
}
