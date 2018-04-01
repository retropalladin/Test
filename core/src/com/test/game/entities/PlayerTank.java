package com.test.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.test.game.Level;
import com.test.game.utils.LevelInputManager;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankMoveState;
import com.test.game.utils.Enums.TankShootState;
import com.test.game.utils.PlayerStatsManager;

public class PlayerTank extends NpcTank {


    public PlayerStatsManager playerStatsManager;

    private Direction inputDirection;
    private float respawnInvis;

    public PlayerTank(Level level, Body body) {
        super.init(level, body);
        moveState = TankMoveState.WAITING;
    }

    public void configurePlayerTankType(PlayerStatsManager playerStatsManager, Direction direction) {
        this.playerStatsManager = playerStatsManager;
        this.configureNpcTankType(Constants.Physics.CATEGORY_ALLY_TANK, playerStatsManager.getConstHp(), playerStatsManager.getTankType(), playerStatsManager.getPrevLevelAmmo(),direction);
    }

    public void update(float delta) {
        if(respawnInvis > 0){
            respawnInvis -= delta;
            if(respawnInvis <=0) {
                this.setAlive(true);
                this.setHp(playerStatsManager.getConstHp());
            }
        }

        if (shootState == TankShootState.RELOADING)
            endShoot(delta * Constants.Settings.PLAYER_RELOAD_MUL);

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

    }

    @Override
    protected boolean beginShoot() {
        ammoType = playerStatsManager.shootCurrentPlayerAmmo();
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
        respawnInvis = Constants.Settings.RESPAWN_INVIS;
        moveState = TankMoveState.WAITING;
        shootState = TankShootState.READY;
    }
}
