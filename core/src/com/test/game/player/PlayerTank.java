package com.test.game.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.test.game.entities.Bullet;
import com.test.game.entities.NpcTank;
import com.test.game.level.Level;
import com.test.game.inputs.GameplayInputManager;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankMoveState;
import com.test.game.utils.Enums.TankShootState;

public class PlayerTank extends NpcTank {
    
    public float respawnInvisibility;
    public PlayerManager playerManager;

    private Direction inputDirection;

    public PlayerTank(Level level, Body body) {
        super.init(level, body);
        moveState = TankMoveState.WAITING;
        respawnInvisibility = Level.RESPAWN_INVIS;
    }

    public void configurePlayerTankType(PlayerManager playerManager, Direction direction) {
        this.playerManager = playerManager;
        this.configureNpcTankType(Constants.Physics.CATEGORY_ALLY_TANK, playerManager.getConstHp(), playerManager.getTankType(), playerManager.getPrevLevelAmmo(),direction);
    }

    public void update(float delta) {
        if(respawnInvisibility > 0){
            endRespawn(delta);
        }

        if (shootState == TankShootState.RELOADING) {
            endShoot(delta * Constants.Settings.PLAYER_RELOAD_MUL);
        }

        if (moveState == TankMoveState.ROTATING) {
            endRotate(delta);
        }

        if(moveState != TankMoveState.ROTATING && shootState == TankShootState.READY && GameplayInputManager.instance.gameplayInput.shoot()){
            beginShoot();
        }

        inputDirection = GameplayInputManager.instance.gameplayInput.getPlayerDesiredDirection();

        if(inputDirection != null && this.direction != inputDirection && moveState != TankMoveState.ON_MOVE) {
            beginRotate(inputDirection);
        }

        if (moveState == TankMoveState.ON_MOVE){
            endMove(Constants.Physics.PLAYER_TANK_MOVE_MASK, delta, inputDirection);
        } else {
            if (inputDirection != null && moveState != TankMoveState.ROTATING) {
                beginMove(Constants.Physics.PLAYER_TANK_MOVE_MASK);
            }
        }
    }


    @Override
    public void takeDamage(byte damage) {
        decreaseHp(damage);
        playerManager.setRealHp(hp);
    }

    @Override
    protected boolean beginShoot() {
        ammoType = playerManager.shootCurrentPlayerAmmo();
        if (ammoType != null){
            switch (ammoType) {
                case NORMAL_BULLET:
                    reloadTime = isSpeedUp ? Bullet.NORMAL_BULLET_RELOAD_SU : Bullet.NORMAL_BULLET_RELOAD;
                    level.spawnCorrectedBullet(body.getPosition().x, body.getPosition().y, ammoType, direction, true);
                    shootState = TankShootState.RELOADING;
                    break;
                case PLASMA_BULLET:
                    reloadTime = isSpeedUp ? Bullet.PLASMA_BULLET_RELOAD_SU : Bullet.PLASMA_BULLET_RELOAD;
                    level.spawnCorrectedBullet(body.getPosition().x, body.getPosition().y, ammoType, direction, true);
                    shootState = TankShootState.RELOADING;
                    break;
                case AP_BULLET:
                    reloadTime = isSpeedUp ? Bullet.AP_BULLET_RELOAD_SU : Bullet.AP_BULLET_RELOAD;
                    level.spawnCorrectedBullet(body.getPosition().x, body.getPosition().y, ammoType, direction, true);
                    shootState = TankShootState.RELOADING;
                    break;
                case RAP_BULLET:
                    reloadTime = isSpeedUp ? Bullet.RAP_BULLET_RELOAD_SU : Bullet.RAP_BULLET_RELOAD;
                    level.spawnCorrectedBullet(body.getPosition().x, body.getPosition().y, ammoType, direction, true);
                    shootState = TankShootState.RELOADING;
                    break;
                case DOUBLE_NORMAL_BULLET:
                    reloadTime = isSpeedUp ? Bullet.DOUBLE_NORMAL_BULLET_RELOAD_SU : Bullet.DOUBLE_NORMAL_BULLET_RELOAD;
                    level.spawnCorrectedDoubleBullet(body.getPosition().x, body.getPosition().y, ammoType, direction, true);
                    shootState = TankShootState.RELOADING;
                    break;
                case DOUBLE_PLASMA_BULLET:
                    reloadTime = isSpeedUp ? Bullet.DOUBLE_PLASMA_BULLET_RELOAD_SU : Bullet.DOUBLE_PLASMA_BULLET_RELOAD;
                    level.spawnCorrectedDoubleBullet(body.getPosition().x, body.getPosition().y, ammoType, direction, true);
                    shootState = TankShootState.RELOADING;
                    break;
                case ENERGY_DRINK:
                    level.beginPlayerSpeedUp();
                    break;
                case TIME_STOP:
                    level.beginEnemyFreeze();
                    break;
            }
            return true;
        } else {
            return false;
        }
    }

    public void respawn(short posX, short posY){
        setGridCoordinates(posX,posY);
        this.body.setLinearVelocity(Vector2.Zero);
        this.body.setTransform(posX * Constants.Physics.CELL_SIZE + TANK_MARGIN, posY * Constants.Physics.CELL_SIZE + TANK_MARGIN,0);
        moveDestination.x = body.getPosition().x;
        moveDestination.y = body.getPosition().y;
        rotatePosition = rotateDestination;
        reloadTime = 0;
        respawnInvisibility = Level.RESPAWN_INVIS;
        moveState = TankMoveState.WAITING;
        shootState = TankShootState.READY;
        this.setHp(playerManager.getConstHp());
        playerManager.setRealHp(hp);
        this.setAlive(true);
    }

    public void endRespawn(float delta) {
        respawnInvisibility -= delta;
        if(respawnInvisibility <=0) {
            respawnInvisibility = 0;
        }
    }
}
