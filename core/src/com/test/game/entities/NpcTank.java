package com.test.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.test.game.Level;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankState;
import com.test.game.utils.Enums.TankType;
import com.test.game.utils.MaterialEntity;

public class NpcTank extends MaterialEntity implements Pool.Poolable {

    public Direction direction;

    protected float deltaX;
    protected float deltaY;
    protected Vector2 destination;

    protected TankType type;
    protected TankState state;
    protected Level level;

    public void init(Level level, Body body) {
        this.level = level;
        this.setAlive(true);
        this.setBody(body);
    }

    public void configureNpcTankType(short category, TankType type, Direction direction) {
        this.direction = direction;
        this.setCategory(category);
        this.type = type;
    }

    protected boolean beginMove() {
        switch (direction){
            case LEFT:
                if(level.objectsMatrix[gridY][gridX-1] == 0) {
                    state = TankState.ON_MOVE;
                    destination.x = Constants.CELL_SIZE * (gridX - 1) + Constants.TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = Constants.CATEGORY_TANK_ON_MOVE;
                    gridX--;
                    body.applyLinearImpulse(Constants.TANK_LEFT_IMPULSE, body.getWorldCenter(), true);
                }
                break;
            case RIGHT:
                if(level.objectsMatrix[gridY][gridX+1] == 0) {
                    state = TankState.ON_MOVE;
                    destination.x = Constants.CELL_SIZE * (gridX + 1) + Constants.TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = Constants.CATEGORY_TANK_ON_MOVE;
                    gridX++;
                    body.applyLinearImpulse(Constants.TANK_RIGHT_IMPULSE, body.getWorldCenter(), true);
                }
                break;
            case UP:
                if(level.objectsMatrix[gridY+1][gridX] == 0) {
                    state = TankState.ON_MOVE;
                    destination.y = Constants.CELL_SIZE * (gridY + 1) + Constants.TANK_MARGIN;
                    level.objectsMatrix[gridY][gridX] = Constants.CATEGORY_TANK_ON_MOVE;
                    gridY++;
                    body.applyLinearImpulse(Constants.TANK_UP_IMPULSE, body.getWorldCenter(), true);
                }
                break;
            case DOWN:
                if(level.objectsMatrix[gridY-1][gridX] == 0) {
                    state = TankState.ON_MOVE;
                    destination.y = Constants.CELL_SIZE * (gridY - 1) + Constants.TANK_MARGIN;
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
        deltaX = body.getPosition().x - destination.x;
        deltaY = body.getPosition().y - destination.y;
        switch (direction){
            case LEFT:
                if(deltaX < Constants.TANK_MOVE_CATCH_EPS) {
                    state = TankState.WAITING;
                    level.objectsMatrix[gridY][gridX +1] = 0;
                }
                break;
            case RIGHT:
                if(deltaX > -Constants.TANK_MOVE_CATCH_EPS) {
                    state = TankState.WAITING;
                    level.objectsMatrix[gridY][gridX -1] = 0;
                }
                break;
            case UP:
                if(deltaY > -Constants.TANK_MOVE_CATCH_EPS) {
                    state = TankState.WAITING;
                    level.objectsMatrix[gridY -1][gridX] = 0;
                }
                break;
            case DOWN:
                if(deltaY < Constants.TANK_MOVE_CATCH_EPS) {
                    state = TankState.WAITING;
                    level.objectsMatrix[gridY +1][gridX] = 0;
                }
                break;
        }
        if(state == TankState.WAITING)
        {
            body.setLinearVelocity(Vector2.Zero);
            body.setTransform(destination,0);
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
