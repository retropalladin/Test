package com.test.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import com.test.game.Level;
import com.test.game.LevelInputManager;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankState;
import com.test.game.utils.Enums.TankType;
import com.test.game.utils.Utils;

public class PlayerTank extends NpcTank {

    public PlayerTank(Level level, Body body) {
        super.init(level, body);
        state = TankState.WAITING;
    }

    public void configurePlayerTankType(short category, TankType type, Direction direction) {
        this.configureNpcTankType(category,type,direction);
    }

    public void update(float delta) {
        if (state == TankState.ON_MOVE){
            endMove();
        }
        if (state == TankState.ROTATING) {
            endRotate(delta);
        }

        Direction direction = LevelInputManager.instance.levelInput.getPlayerDesiredDirectiond();

        if(direction != null && this.direction != direction && state != TankState.ON_MOVE) {
            beginRotate(direction);
        }

        if (direction != null && state != TankState.ON_MOVE && state!= TankState.ROTATING) {
            beginMove(Constants.PLAYER_TANK_MOVE_MASK);
        }
    }
}
