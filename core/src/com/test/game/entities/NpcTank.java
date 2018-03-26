package com.test.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.test.game.Level;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankState;
import com.test.game.utils.Enums.TankType;
import com.test.game.utils.MaterialEntity;
import com.test.game.utils.Utils;

import java.util.Random;

public class NpcTank extends MaterialEntity implements Pool.Poolable {

    public Direction direction;

    private float deltaX;
    private float deltaY;
    private Vector2 moveDestination;

    private int rotateDirection;
    private float rotatePosition;
    private float rotateDestination;
    private float rotationSpeed;

    public TankType type;
    protected TankState state;
    protected Level level;

    public void init(Level level, Body body) {
        this.level = level;
        this.setAlive(true);
        this.setBody(body);
        moveDestination = new Vector2(body.getPosition().x,body.getPosition().y);
    }

    public void configureNpcTankType(short category, TankType type, Direction direction) {
        this.direction = direction;
        this.setCategory(category);
        this.type = type;
        switch (type){
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
                    state = TankState.ON_MOVE;
                    moveDestination.x = Constants.CELL_SIZE * (gridX - 1) + Constants.TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = Constants.CATEGORY_TANK_ON_MOVE;
                    gridX--;
                    body.applyLinearImpulse(Constants.TANK_LEFT_IMPULSE, body.getWorldCenter(), true);
                }
                break;
            case RIGHT:
                if((level.objectsMatrix[gridY][gridX+1] & MoveMask) != 0) {
                    state = TankState.ON_MOVE;
                    moveDestination.x = Constants.CELL_SIZE * (gridX + 1) + Constants.TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = Constants.CATEGORY_TANK_ON_MOVE;
                    gridX++;
                    body.applyLinearImpulse(Constants.TANK_RIGHT_IMPULSE, body.getWorldCenter(), true);
                }
                break;
            case UP:
                if((level.objectsMatrix[gridY+1][gridX] & MoveMask) != 0) {
                    state = TankState.ON_MOVE;
                    moveDestination.y = Constants.CELL_SIZE * (gridY + 1) + Constants.TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = Constants.CATEGORY_TANK_ON_MOVE;
                    gridY++;
                    body.applyLinearImpulse(Constants.TANK_UP_IMPULSE, body.getWorldCenter(), true);
                }
                break;
            case DOWN:
                if((level.objectsMatrix[gridY-1][gridX] & MoveMask) != 0) {
                    state = TankState.ON_MOVE;
                    moveDestination.y = Constants.CELL_SIZE * (gridY - 1) + Constants.TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = Constants.CATEGORY_TANK_ON_MOVE;
                    gridY--;
                    body.applyLinearImpulse(Constants.TANK_DOWN_IMPULSE, body.getWorldCenter(), true);
                }
                break;
        }
        if(state == TankState.ON_MOVE){
            level.objectsMatrix[gridY][gridX] = category;
            return true;
        }
        return false;
    }

    protected boolean endMove(){
        deltaX = body.getPosition().x - moveDestination.x;
        deltaY = body.getPosition().y - moveDestination.y;
        switch (direction){
            case LEFT:
                if(deltaX < Constants.TANK_MOVE_CATCH_EPS) {
                    state = TankState.WAITING;
                    level.objectsMatrix[gridY][gridX +1] = Constants.CATEGORY_EMPTY;
                }
                break;
            case RIGHT:
                if(deltaX > -Constants.TANK_MOVE_CATCH_EPS) {
                    state = TankState.WAITING;
                    level.objectsMatrix[gridY][gridX -1] = Constants.CATEGORY_EMPTY;
                }
                break;
            case UP:
                if(deltaY > -Constants.TANK_MOVE_CATCH_EPS) {
                    state = TankState.WAITING;
                    level.objectsMatrix[gridY -1][gridX] = Constants.CATEGORY_EMPTY;
                }
                break;
            case DOWN:
                if(deltaY < Constants.TANK_MOVE_CATCH_EPS) {
                    state = TankState.WAITING;
                    level.objectsMatrix[gridY +1][gridX] = Constants.CATEGORY_EMPTY;
                }
                break;
        }
        if(state == TankState.WAITING)
        {
            body.setLinearVelocity(Vector2.Zero);
            body.setTransform(moveDestination,0);
            return true;
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
        this.state = TankState.ROTATING;
        this.direction = direction;
        return true;
    }

    protected boolean endRotate(float delta){
        rotatePosition += rotateDirection * rotationSpeed * delta;
        if((rotatePosition - rotateDestination)*rotateDirection > 0)
        {
            rotatePosition = rotateDestination;
            state = TankState.WAITING;
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        this.setAlive(false);
        this.setBody(null);
        this.setCategory((short) 0);
        this.setHp(0);
    }
}
