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

    public PlayerTank(Level level, Body body) {
        super.init(level, body);
    }

    public void configurePlayerTankType(short category, TankType type, Direction direction) {
        this.configureNpcTankType(category,type,direction);
    }

    public void update()
    {
        // Demo
        body.setLinearVelocity(Vector2.Zero);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction = Direction.LEFT;
            body.applyLinearImpulse(Constants.TANK_LEFT_IMPULSE,body.getWorldCenter(),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction = Direction.RIGHT;
            body.applyLinearImpulse(Constants.TANK_RIGHT_IMPULSE,body.getWorldCenter(),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            direction = Direction.UP;
            body.applyLinearImpulse(Constants.TANK_UP_IMPULSE,body.getWorldCenter(),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            direction = Direction.DOWN;
            body.applyLinearImpulse(Constants.TANK_DOWN_IMPULSE,body.getWorldCenter(),true);
        }
    }
}
