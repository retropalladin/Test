package com.test.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.test.game.Level;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.AmmoType;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankMoveState;
import com.test.game.utils.Enums.TankShootState;
import com.test.game.utils.Enums.TankType;
import com.test.game.utils.MaterialEntity;
import com.test.game.utils.Utils;

import java.util.Locale;

public class NpcTank extends MaterialEntity implements Pool.Poolable {

    public Direction direction;

    private boolean isAlly;
    private int shieldHp;

    private short prevCategory;
    private short nextCategory;

    private float deltaX;
    private float deltaY;
    private Vector2 moveDestination;

    private int rotateDirection;
    private float rotatePosition;
    private float rotateDestination;
    private float rotationSpeed;

    private float reloadTime;
    public AmmoType ammoType;

    public TankType tankType;
    protected TankMoveState moveState;
    protected TankShootState shootState;
    protected Level level;

    public void init(Level level, Body body) {
        this.level = level;
        this.setAlive(true);
        this.setBody(body);
        moveDestination = new Vector2(body.getPosition().x, body.getPosition().y);
        moveState = TankMoveState.WAITING;
        shootState = TankShootState.READY;
        reloadTime = 0;
        ammoType = AmmoType.NORMAL_BULLET;
    }

    public void configureNpcTankType(short category, int hp, int shieldHp,TankType tankType, AmmoType ammoType, Direction direction) {
        this.direction = direction;
        switch (category){
            case Constants.CATEGORY_ALLY_TANK:
                isAlly = true;
                break;
            case Constants.CATEGORY_ENEMY_TANK:
                isAlly = false;
                break;
        }
        this.setCategory(category);
        this.tankType = tankType;
        this.hp = hp;
        this.shieldHp = shieldHp;
        switch (tankType){
            case LIGHT_TANK:
                rotationSpeed = Constants.LIGHT_TANK_ROTATION_SPEED;
                break;
            case HEAVY_TANK:
                rotationSpeed = Constants.HEAVY_TANK_ROTATION_SPEED;
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
        if(shootState == TankShootState.RELOADING)
            endShoot(delta);
        if(shootState == TankShootState.READY)
            beginShoot();
    }

    public void takeDamage(int damage){
        shieldHp -= damage;
        if(shieldHp <= 0)
        {
            if(!decreaseHp(-shieldHp)){
                level.objectsMatrix[gridY][gridX] = Constants.CATEGORY_EMPTY;
            }
            shieldHp = 0;
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
                if((level.objectsMatrix[gridY][gridX-1] & MoveMask) != 0) {
                    moveState = TankMoveState.ON_MOVE;
                    moveDestination.x = Constants.CELL_SIZE * (gridX - 1) + Constants.TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = (short) (Constants.CATEGORY_TANK_ON_MOVE | prevCategory);
                    gridX--;
                    body.applyLinearImpulse(Constants.TANK_LEFT_IMPULSE, body.getWorldCenter(), true);
                }
                break;
            case RIGHT:
                if((level.objectsMatrix[gridY][gridX+1] & MoveMask) != 0) {
                    moveState = TankMoveState.ON_MOVE;
                    moveDestination.x = Constants.CELL_SIZE * (gridX + 1) + Constants.TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = (short) (Constants.CATEGORY_TANK_ON_MOVE | prevCategory);
                    gridX++;
                    body.applyLinearImpulse(Constants.TANK_RIGHT_IMPULSE, body.getWorldCenter(), true);
                }
                break;
            case UP:
                if((level.objectsMatrix[gridY+1][gridX] & MoveMask) != 0) {
                    moveState = TankMoveState.ON_MOVE;
                    moveDestination.y = Constants.CELL_SIZE * (gridY + 1) + Constants.TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = (short) (Constants.CATEGORY_TANK_ON_MOVE | prevCategory);
                    gridY++;
                    body.applyLinearImpulse(Constants.TANK_UP_IMPULSE, body.getWorldCenter(), true);
                }
                break;
            case DOWN:
                if((level.objectsMatrix[gridY-1][gridX] & MoveMask) != 0) {
                    moveState = TankMoveState.ON_MOVE;
                    moveDestination.y = Constants.CELL_SIZE * (gridY - 1) + Constants.TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = (short) (Constants.CATEGORY_TANK_ON_MOVE | prevCategory);
                    gridY--;
                    body.applyLinearImpulse(Constants.TANK_DOWN_IMPULSE, body.getWorldCenter(), true);
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
            if(inputDirection == direction){
                body.setLinearVelocity(Vector2.Zero);
                beginMove(MoveMask);
            }else {
                prevCategory = nextCategory;
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
        switch(ammoType){
                case NORMAL_BULLET:
                    reloadTime = Constants.NORMAL_BULLET_RELOAD;
                    level.spawnCorrectedBullet(body.getPosition().x,body.getPosition().y, ammoType,direction,isAlly);
                    break;
                case PLASMA_BULLET:
                    reloadTime = Constants.PLASMA_BULLET_RELOAD;
                    level.spawnCorrectedBullet(body.getPosition().x,body.getPosition().y, ammoType,direction,isAlly);
                    break;
                case AP_BULLET:
                    reloadTime = Constants.AP_NORMAL_BULLET_RELOAD;
                    level.spawnCorrectedBullet(body.getPosition().x,body.getPosition().y, ammoType,direction,isAlly);
                    break;
                case RAP_BULLET:
                    reloadTime = Constants.RAP_BULLET_RELOAD;
                    level.spawnCorrectedBullet(body.getPosition().x,body.getPosition().y, ammoType,direction,isAlly);
                    break;
                case DOUBLE_NORMAL_BULLET:
                    reloadTime = Constants.DOUBLE_NORMAL_BULLET_RELOAD;
                    level.spawnCorrectedDoubleBullet(body.getPosition().x, body.getPosition().y, ammoType,direction, isAlly);
                    break;
                case DOUBLE_PLASMA_BULLET:
                    reloadTime = Constants.DOUBLE_PLASMA_BULLET_RELOAD;
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


    @Override
    public void setGridCoordinates(short gridX, short gridY){
        this.gridX = gridX;
        this.gridY = gridY;
        prevCategory = Constants.CATEGORY_SPAWN;
    }

    @Override
    public void reset() {
        this.level = null;
        this.setAlive(false);
        this.setBody(null);
        this.setCategory((short) 0);
        this.setHp(0);
    }
}
