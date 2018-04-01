package com.test.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.test.game.level.Level;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.AmmoType;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankMoveState;
import com.test.game.utils.Enums.TankShootState;
import com.test.game.utils.Enums.TankType;
import com.test.game.utils.MaterialEntity;
import com.test.game.utils.Utils;


public class NpcTank extends MaterialEntity implements Pool.Poolable {

    ///////////////////////////////////////////
    /// Constants Settings                  ///
    ///////////////////////////////////////////

    public static final float TANK_WIDTH = Constants.Physics.CELL_SIZE * 0.8f;
    public static final float TANK_WIDTH_H = TANK_WIDTH * 0.5f;
    public static final float TANK_HEIGHT = Constants.Physics.CELL_SIZE * 0.8f;
    public static final float TANK_HEIGHT_H = TANK_HEIGHT * 0.5f;
    public static final float TANK_MARGIN = Constants.Physics.CELL_SIZE_H - TANK_HEIGHT_H;

    ///////////////////////////////////////////
    /// Constants Physics                   ///
    ///////////////////////////////////////////

    public static final float LIGHT_TANK_DENSITY = 0.3f;
    public static final float HEAVY_TANK_DENSITY = 0.4f;
    public static final float TANK_FRICTION = 0f;
    public static final float TANK_RESTITUTION = 0f;

    public static final float TANK_IMPULSE = 4.0f;
    public static final float LIGHT_TANK_ROTATION_SPEED = 600f;
    public static final float HEAVY_TANK_ROTATION_SPEED = 500f;

    public final Vector2 TANK_UP_IMPULSE = new Vector2(0, TANK_IMPULSE);
    public final Vector2 TANK_DOWN_IMPULSE = new Vector2(0, -TANK_IMPULSE);
    public final Vector2 TANK_RIGHT_IMPULSE = new Vector2(TANK_IMPULSE, 0);
    public final Vector2 TANK_LEFT_IMPULSE = new Vector2(-TANK_IMPULSE, 0);

    ///////////////////////////////////////////
    /// END CONSTANTS                       ///
    ///////////////////////////////////////////

    public Direction direction;

    private boolean isAlly;
    private boolean isFreeze;

    private short prevCategory;
    private short nextCategory;

    protected Vector2 moveDestination;
    private float deltaX;
    private float deltaY;

    protected float rotatePosition;
    protected float rotateDestination;
    private int rotateDirection;
    private float rotationSpeed;

    public AmmoType ammoType;
    protected float reloadTime;

    public TankType tankType;
    protected TankMoveState moveState;
    protected TankShootState shootState;
    protected Level level;

    public void init(Level level, Body body) {
        this.level = level;
        this.setAlive(false);
        this.setBody(body);
        moveDestination = new Vector2(body.getPosition().x, body.getPosition().y);
        moveState = TankMoveState.WAITING;
        shootState = TankShootState.READY;
        reloadTime = 0;
    }

    public void configureNpcTankType(short category, byte hp, TankType tankType, AmmoType ammoType, Direction direction) {
        this.setAlive(true);
        this.direction = direction;
        switch (category){
            case Constants.Physics.CATEGORY_ALLY_TANK:
                isAlly = true;
                break;
            case Constants.Physics.CATEGORY_ENEMY_TANK:
                isAlly = false;
                break;
        }
        this.setCategory(category);
        this.tankType = tankType;
        this.hp = hp;
        switch (tankType){
            case LIGHT_TANK:
                rotationSpeed = LIGHT_TANK_ROTATION_SPEED;
                break;
            case HEAVY_TANK:
                rotationSpeed = HEAVY_TANK_ROTATION_SPEED;
                break;
        }
        switch(direction){
            case RIGHT:
                rotatePosition = rotateDestination = 0;
                break;
            case UP:
                rotatePosition = rotateDestination = 90;
                break;
            case LEFT:
                rotatePosition = rotateDestination = 180;
                break;
            case DOWN:
                rotatePosition = rotateDestination = 270;
                break;
        }
        this.ammoType = ammoType;
    }

    public void update(float delta){
        if(!isFreeze) {
            if (shootState == TankShootState.RELOADING)
                endShoot(delta * Constants.Settings.PLAYER_RELOAD_MUL);
            if(moveState != TankMoveState.ROTATING && shootState == TankShootState.READY){
                beginShoot();
            }

        }
    }

    public void takeDamage(byte damage){
        if(!decreaseHp(damage)){
            if(moveState == TankMoveState.ON_MOVE){
                switch (direction){
                    case LEFT:
                        level.objectsMatrix[gridY][gridX +1] = prevCategory;
                        break;
                    case RIGHT:
                        level.objectsMatrix[gridY][gridX -1] = prevCategory;
                        break;
                    case UP:
                        level.objectsMatrix[gridY -1][gridX] = prevCategory;
                        break;
                    case DOWN:
                        level.objectsMatrix[gridY +1][gridX] = prevCategory;
                        break;
                }
                level.objectsMatrix[gridY][gridX] = nextCategory;
            } else {
                level.objectsMatrix[gridY][gridX] = prevCategory;
            }
        }
    }

    public float getRotatePosition(){
        if(rotatePosition < 0)
            return rotatePosition + 360;
        if(rotatePosition >= 360)
            return  rotatePosition - 360;
        return  rotatePosition;
    }

    protected boolean beginMove(short MoveMask) {
        switch (direction){
            case LEFT:
                if((level.objectsMatrix[gridY][gridX-1] & MoveMask) == 0) {
                    moveState = TankMoveState.ON_MOVE;
                    moveDestination.x = Constants.Physics.CELL_SIZE * (gridX - 1) + TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = (short) (Constants.Physics.CATEGORY_TANK_ON_MOVE | prevCategory);
                    gridX--;
                    body.applyLinearImpulse(TANK_LEFT_IMPULSE, body.getWorldCenter(), true);
                }
                break;
            case RIGHT:
                if((level.objectsMatrix[gridY][gridX+1] & MoveMask) == 0) {
                    moveState = TankMoveState.ON_MOVE;
                    moveDestination.x = Constants.Physics.CELL_SIZE * (gridX + 1) + TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = (short) (Constants.Physics.CATEGORY_TANK_ON_MOVE | prevCategory);
                    gridX++;
                    body.applyLinearImpulse(TANK_RIGHT_IMPULSE, body.getWorldCenter(), true);
                }
                break;
            case UP:
                if((level.objectsMatrix[gridY+1][gridX] & MoveMask) == 0) {
                    moveState = TankMoveState.ON_MOVE;
                    moveDestination.y = Constants.Physics.CELL_SIZE * (gridY + 1) + TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = (short) (Constants.Physics.CATEGORY_TANK_ON_MOVE | prevCategory);
                    gridY++;
                    body.applyLinearImpulse(TANK_UP_IMPULSE, body.getWorldCenter(), true);
                }
                break;
            case DOWN:
                if((level.objectsMatrix[gridY-1][gridX] & MoveMask) == 0) {
                    moveState = TankMoveState.ON_MOVE;
                    moveDestination.y = Constants.Physics.CELL_SIZE * (gridY - 1) + TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = (short) (Constants.Physics.CATEGORY_TANK_ON_MOVE | prevCategory);
                    gridY--;
                    body.applyLinearImpulse(TANK_DOWN_IMPULSE, body.getWorldCenter(), true);
                }
                break;
        }
        if(moveState == TankMoveState.ON_MOVE){
            nextCategory = level.objectsMatrix[gridY][gridX];
            level.objectsMatrix[gridY][gridX] = (short) (category | level.objectsMatrix[gridY][gridX]);
            return true;
        }
        return false;
    }

    protected boolean endMove(short MoveMask, float delta, Direction inputDirection){
        deltaX = moveDestination.x - body.getPosition().x;
        deltaY = moveDestination.y - body.getPosition().y;
        switch (direction){
            case LEFT:
                if(deltaX > body.getLinearVelocity().x * delta) {
                    moveState = TankMoveState.WAITING;
                    level.objectsMatrix[gridY][gridX +1] = prevCategory;
                }
                break;
            case RIGHT:
                if(deltaX < body.getLinearVelocity().x * delta) {
                    moveState = TankMoveState.WAITING;
                    level.objectsMatrix[gridY][gridX -1] = prevCategory;
                }
                break;
            case UP:
                if(deltaY < body.getLinearVelocity().y * delta) {
                    moveState = TankMoveState.WAITING;
                    level.objectsMatrix[gridY -1][gridX] = prevCategory;
                }
                break;
            case DOWN:
                if(deltaY > body.getLinearVelocity().y * delta) {
                    moveState = TankMoveState.WAITING;
                    level.objectsMatrix[gridY +1][gridX] = prevCategory;
                }
                break;
        }
        if(moveState == TankMoveState.WAITING)
        {
            prevCategory = nextCategory;
            if(inputDirection == direction){
                body.setLinearVelocity(Vector2.Zero);
                beginMove(MoveMask);
            }else {
                body.setLinearVelocity(Vector2.Zero);
                body.setTransform(moveDestination, 0);
                return true;
            }
        }
        return false;
    }

    protected boolean beginRotate(Direction direction){
        if(this.direction == direction)
            return false;
        rotatePosition += Utils.random.nextFloat()-0.5f;
        switch (direction){
            case RIGHT:
                rotateDestination = 0;
                if(rotatePosition > 180) {
                    rotateDirection = 1;
                    rotatePosition -= 360;
                }
                else
                    rotateDirection = -1;
                break;
            case UP:
                rotateDestination = 90;
                if(rotatePosition > 270 || rotatePosition < 90) {
                    if(rotatePosition > 270)
                        rotatePosition -= 360;
                    rotateDirection = 1;
                }
                else
                    rotateDirection = -1;
                break;
            case LEFT:
                rotateDestination = 180;
                if(rotatePosition > 180 || rotatePosition < 0) {
                    if(rotatePosition < 0)
                        rotatePosition += 360;
                    rotateDirection = -1;
                }
                else
                    rotateDirection = 1;
                break;
            case DOWN:
                rotateDestination = 270;
                if(rotatePosition > 270 || rotatePosition < 90) {
                    if(rotatePosition < 90)
                        rotatePosition += 360;
                    rotateDirection = -1;
                }
                else
                    rotateDirection = 1;
                break;
        }
        this.moveState = TankMoveState.ROTATING;
        this.direction = direction;
        return true;
    }

    protected boolean endRotate(float delta){
        rotatePosition += rotateDirection * rotationSpeed * delta;
        if((rotatePosition - rotateDestination)*rotateDirection > 0)
        {
            rotatePosition = rotateDestination;
            moveState = TankMoveState.WAITING;
            return true;
        }
        return false;
    }

    protected boolean beginShoot(){
        if(category == Constants.Physics.CATEGORY_ALLY_TANK){
            if(level.playerTank != null)
                ammoType = level.playerTank.playerStatsManager.shootCurrentAllyAmmo();
            else
                ammoType = level.deadPlayerTank.playerStatsManager.shootCurrentAllyAmmo();
        }
        switch(ammoType){
            case NORMAL_BULLET:
                reloadTime = Bullet.NORMAL_BULLET_RELOAD;
                level.spawnCorrectedBullet(body.getPosition().x,body.getPosition().y, ammoType,direction,isAlly);
                break;
            case PLASMA_BULLET:
                reloadTime = Bullet.PLASMA_BULLET_RELOAD;
                level.spawnCorrectedBullet(body.getPosition().x,body.getPosition().y, ammoType,direction,isAlly);
                break;
            case AP_BULLET:
                reloadTime = Bullet.AP_NORMAL_BULLET_RELOAD;
                level.spawnCorrectedBullet(body.getPosition().x,body.getPosition().y, ammoType,direction,isAlly);
                break;
            case RAP_BULLET:
                reloadTime = Bullet.RAP_BULLET_RELOAD;
                level.spawnCorrectedBullet(body.getPosition().x,body.getPosition().y, ammoType,direction,isAlly);
                break;
            case DOUBLE_NORMAL_BULLET:
                reloadTime = Bullet.DOUBLE_NORMAL_BULLET_RELOAD;
                level.spawnCorrectedDoubleBullet(body.getPosition().x, body.getPosition().y, ammoType,direction, isAlly);
                break;
            case DOUBLE_PLASMA_BULLET:
                reloadTime = Bullet.DOUBLE_PLASMA_BULLET_RELOAD;
                level.spawnCorrectedDoubleBullet(body.getPosition().x, body.getPosition().y, ammoType,direction, isAlly);
                break;
            default:
                return false;
        }
        shootState = TankShootState.RELOADING;
        return true;
    }

    protected boolean endShoot(float delta){
        reloadTime -= delta;
        if(reloadTime < 0) {
            shootState = TankShootState.READY;
            return true;
        }
        return false;
    }

    public void freeze(){
        if(category == Constants.Physics.CATEGORY_ENEMY_TANK) {
            body.setLinearVelocity(Vector2.Zero);isFreeze = true;
            isFreeze = true;
        }
    }

    public void unfreeze(){
        if(category == Constants.Physics.CATEGORY_ENEMY_TANK) {
            if(moveState == TankMoveState.ON_MOVE && isFreeze) {
                switch (direction){
                    case LEFT:
                        body.applyLinearImpulse(TANK_LEFT_IMPULSE, body.getWorldCenter(), true);
                        break;
                    case RIGHT:
                        body.applyLinearImpulse(TANK_RIGHT_IMPULSE, body.getWorldCenter(), true);
                        break;
                    case UP:
                        body.applyLinearImpulse(TANK_UP_IMPULSE, body.getWorldCenter(), true);
                        break;
                    case DOWN:
                        body.applyLinearImpulse(TANK_DOWN_IMPULSE, body.getWorldCenter(), true);
                        break;
                }
            }
        }
        isFreeze = false;
    }
    @Override
    public void setGridCoordinates(short gridX, short gridY){
        this.gridX = gridX;
        this.gridY = gridY;
        prevCategory = Constants.Physics.CATEGORY_SPAWN;
    }

    @Override
    public void reset() {
        this.level = null;
        this.setAlive(false);
        this.setBody(null);
        this.setCategory((short) 0);
        this.setHp((byte) 0);
    }
}
