package com.test.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.test.game.Level;
import com.test.game.LevelInputManager;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankState;
import com.test.game.utils.Enums.TankType;

public class PlayerTank extends NpcTank {

    public PlayerTank(Level level, Body body) {
        super.init(level, body);
        state = TankState.WAITING;
        destination = new Vector2(body.getPosition().x,body.getPosition().y);
    }

    public void configurePlayerTankType(short category, TankType type, Direction direction) {
        this.configureNpcTankType(category,type,direction);
    }

    public void update() {
        if (state == TankState.ON_MOVE){
            endMove();
        }
        Direction direction = LevelInputManager.instance.levelInput.getPlayerDesiredDirectiond();
        if (direction != null && state != TankState.ON_MOVE && state!= TankState.ROTATING) {
            this.direction = direction;
            beginMove(Constants.PLAYER_TANK_MOVE_MASK);
        }
    }
}
