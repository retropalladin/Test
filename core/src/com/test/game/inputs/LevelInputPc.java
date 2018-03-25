package com.test.game.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.LevelInput;

public class LevelInputPc extends InputAdapter implements LevelInput {

    private final Object sync = new Object();
    private int keyUpPos = 0, keyUpi = 0;
    private int[] playerDesiredDirection = new int[5];
    private boolean cameraDebugOn;

    @Override
    public boolean cameraDebugOn() {
        if(cameraDebugOn) {
            cameraDebugOn = false;
            return true;
        }
        return false;
    }
    public boolean debugCameraMoveLeft(){
        return Gdx.input.isKeyPressed(Input.Keys.LEFT);
    }
    public boolean debugCameraMoveRight(){
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }
    public boolean debugCameraMoveUp(){
        return Gdx.input.isKeyPressed(Input.Keys.UP);
    }
    public boolean debugCameraMoveDown(){
        return Gdx.input.isKeyPressed(Input.Keys.DOWN);
    }
    public Direction getPlayerDesiredDirectiond(){
        synchronized (sync){
            switch (playerDesiredDirection[0])
            {
                case Input.Keys.W:
                    return Direction.UP;
                case Input.Keys.S:
                    return Direction.DOWN;
                case Input.Keys.A:
                    return Direction.LEFT;
                case Input.Keys.D:
                    return Direction.RIGHT;
            }
        }
        return null;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        synchronized (sync){
            switch(keycode){
                case Input.Keys.SPACE:
                    cameraDebugOn = true;
                case Input.Keys.W:
                case Input.Keys.S:
                case Input.Keys.A:
                case Input.Keys.D:
                    playerDesiredDirection[3] = playerDesiredDirection[2];
                    playerDesiredDirection[2] = playerDesiredDirection[1];
                    playerDesiredDirection[1] = playerDesiredDirection[0];
                    playerDesiredDirection[0] = keycode;
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean keyUp (int keycode)
    {
        synchronized (sync){
            switch(keycode){
                case Input.Keys.W:
                case Input.Keys.S:
                case Input.Keys.A:
                case Input.Keys.D:
                    for(keyUpi = 0; keyUpi < 4; keyUpi++)
                        if(playerDesiredDirection[keyUpi]==keycode)
                        {
                            keyUpPos = keyUpi;
                            break;
                        }
                    for(keyUpi = keyUpPos; keyUpi <= 3; keyUpi++)
                        playerDesiredDirection[keyUpi] = playerDesiredDirection[keyUpi+1];
                    break;
            }
        }
        return true;
    }
}
