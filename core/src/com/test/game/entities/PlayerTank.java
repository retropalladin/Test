package com.test.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.test.game.Level;
import com.test.game.LevelInputManager;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankState;
import com.test.game.utils.Enums.TankType;

public class PlayerTank extends NpcTank {

    private TankState state;
    private Vector2 destination;
    private float deltaX;
    private float deltaY;

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
            deltaX = body.getPosition().x - destination.x;
            deltaY = body.getPosition().y - destination.y;
            switch (direction){
                case LEFT:
                    if(deltaX < Constants.TANK_MOVE_CATCH_EPS) {
                        state = TankState.WAITING;
                        level.objectsMatrix[y][x+1] = 0;
                        body.setLinearVelocity(Vector2.Zero);
                        body.setTransform(destination,0);
                    }
                    break;
                case RIGHT:
                    if(deltaX > -Constants.TANK_MOVE_CATCH_EPS) {
                        state = TankState.WAITING;
                        level.objectsMatrix[y][x-1] = 0;
                        body.setLinearVelocity(Vector2.Zero);
                        body.setTransform(destination,0);
                    }
                    break;
                case UP:
                    if(deltaY > -Constants.TANK_MOVE_CATCH_EPS) {
                        state = TankState.WAITING;
                        level.objectsMatrix[y-1][x] = 0;
                        body.setLinearVelocity(Vector2.Zero);
                        body.setTransform(destination,0);
                    }
                    break;
                case DOWN:
                    if(deltaY < Constants.TANK_MOVE_CATCH_EPS) {
                        state = TankState.WAITING;
                        level.objectsMatrix[y+1][x] = 0;
                        body.setLinearVelocity(Vector2.Zero);
                        body.setTransform(destination,0);
                    }
                    break;
            }
        }
        Direction direction = LevelInputManager.instance.levelInput.getPlayerMoveDirection();
        if (direction != null && state != TankState.ON_MOVE) {
            this.direction = direction;
            switch (direction){
                case LEFT:
                    if(level.objectsMatrix[y][x-1] == 0) {
                        state = TankState.ON_MOVE;
                        destination.x = Constants.CELL_SIZE * (x - 1) + Constants.TANK_MARGIN;
                        level.objectsMatrix[y][x] = Constants.CATEGORY_TANK_ON_MOVE;
                        x--;
                        level.objectsMatrix[y][x] = Constants.CATEGORY_ALLY_TANK;
                        body.applyLinearImpulse(Constants.TANK_LEFT_IMPULSE, body.getWorldCenter(), true);
                    }
                    break;
                case RIGHT:
                    if(level.objectsMatrix[y][x+1] == 0) {
                        state = TankState.ON_MOVE;
                        destination.x = Constants.CELL_SIZE * (x + 1) + Constants.TANK_MARGIN;
                        level.objectsMatrix[y][x] = Constants.CATEGORY_TANK_ON_MOVE;
                        x++;
                        level.objectsMatrix[y][x] = Constants.CATEGORY_ALLY_TANK;
                        body.applyLinearImpulse(Constants.TANK_RIGHT_IMPULSE, body.getWorldCenter(), true);
                    }
                    break;
                case UP:
                    if(level.objectsMatrix[y+1][x] == 0) {
                        state = TankState.ON_MOVE;
                        destination.y = Constants.CELL_SIZE * (y + 1) + Constants.TANK_MARGIN;
                        level.objectsMatrix[y][x] = Constants.CATEGORY_TANK_ON_MOVE;
                        y++;
                        level.objectsMatrix[y][x] = Constants.CATEGORY_ALLY_TANK;
                        body.applyLinearImpulse(Constants.TANK_UP_IMPULSE, body.getWorldCenter(), true);
                    }
                    break;
                case DOWN:
                    if(level.objectsMatrix[y-1][x] == 0) {
                        state = TankState.ON_MOVE;
                        destination.y = Constants.CELL_SIZE * (y - 1) + Constants.TANK_MARGIN;
                        level.objectsMatrix[y][x] = Constants.CATEGORY_TANK_ON_MOVE;
                        y--;
                        level.objectsMatrix[y][x] = Constants.CATEGORY_ALLY_TANK;
                        body.applyLinearImpulse(Constants.TANK_DOWN_IMPULSE, body.getWorldCenter(), true);
                    }
                    break;
            }
        }
    }
}
