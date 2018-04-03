package com.test.game.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.test.game.entities.Bullet;
import com.test.game.entities.NpcTank;
import com.test.game.level.Level;
import com.test.game.level.LevelInputManager;
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
        respawnInvisibility = Constants.Settings.RESPAWN_INVIS;
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

        if(moveState != TankMoveState.ROTATING && shootState == TankShootState.READY && LevelInputManager.instance.levelInput.shoot()){
            beginShoot();
        }

        inputDirection = LevelInputManager.instance.levelInput.getPlayerDesiredDirection();

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
                    reloadTime = Bullet.NORMAL_BULLET_RELOAD;
                    level.spawnCorrectedBullet(body.getPosition().x, body.getPosition().y, ammoType, direction, true);
                    break;
                case PLASMA_BULLET:
                    reloadTime = Bullet.PLASMA_BULLET_RELOAD;
                    level.spawnCorrectedBullet(body.getPosition().x, body.getPosition().y, ammoType, direction, true);
                    break;
                case AP_BULLET:
                    reloadTime = Bullet.AP_NORMAL_BULLET_RELOAD;
                    level.spawnCorrectedBullet(body.getPosition().x, body.getPosition().y, ammoType, direction, true);
                    break;
                case RAP_BULLET:
                    reloadTime = Bullet.RAP_BULLET_RELOAD;
                    level.spawnCorrectedBullet(body.getPosition().x, body.getPosition().y, ammoType, direction, true);
                    break;
                case DOUBLE_NORMAL_BULLET:
                    reloadTime = Bullet.DOUBLE_NORMAL_BULLET_RELOAD;
                    level.spawnCorrectedDoubleBullet(body.getPosition().x, body.getPosition().y, ammoType, direction, true);
                    break;
                case DOUBLE_PLASMA_BULLET:
                    reloadTime = Bullet.DOUBLE_PLASMA_BULLET_RELOAD;
                    level.spawnCorrectedDoubleBullet(body.getPosition().x, body.getPosition().y, ammoType, direction, true);
                    break;
            }
            shootState = TankShootState.RELOADING;
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
        respawnInvisibility = Constants.Settings.RESPAWN_INVIS;
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
