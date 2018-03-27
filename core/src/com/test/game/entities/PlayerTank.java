package com.test.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.test.game.Level;
import com.test.game.LevelInputManager;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.AmmoType;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankMoveState;
import com.test.game.utils.Enums.TankShootState;
import com.test.game.utils.Enums.TankType;

public class PlayerTank extends NpcTank {

    private Direction inputDirection;

    public PlayerTank(Level level, Body body) {
        super.init(level, body);
        moveState = TankMoveState.WAITING;
    }

    public void configurePlayerTankType(short category, TankType type, AmmoType ammoType, Direction direction) {
        this.configureNpcTankType(category,type,ammoType,direction);
    }

    public void update(float delta) {
        if (shootState == TankShootState.RELOADING) {
            endShoot(delta);
        }
        if (moveState == TankMoveState.ON_MOVE){
            endMove();
        }
        if (moveState == TankMoveState.ROTATING) {
            endRotate(delta);
        }

        inputDirection = LevelInputManager.instance.levelInput.getPlayerDesiredDirection();

        if(inputDirection != null && this.direction != inputDirection && moveState != TankMoveState.ON_MOVE) {
            beginRotate(inputDirection);
        }
        if (inputDirection != null && moveState != TankMoveState.ON_MOVE && moveState != TankMoveState.ROTATING) {
            beginMove(Constants.PLAYER_TANK_MOVE_MASK);
        }

        if((moveState != TankMoveState.ROTATING) && (shootState == TankShootState.READY) && LevelInputManager.instance.levelInput.shoot()){
            beginShoot(true);
        }
    }
}
