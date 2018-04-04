package com.test.game.inputs;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.test.game.utils.Enums.Direction;

public class GameplayInputMobile extends InputAdapter implements GameplayInput {

    private Direction playerDesiredDirection = null;
    private int tmpconst = 180;
    private Rectangle up = new Rectangle(tmpconst,tmpconst,tmpconst,tmpconst);
    private Rectangle down = new Rectangle(tmpconst,3*tmpconst,tmpconst,tmpconst);
    private Rectangle left = new Rectangle(0,2*tmpconst,tmpconst,tmpconst);
    private Rectangle right = new Rectangle(2*tmpconst,2*tmpconst,tmpconst,tmpconst);

    public boolean shoot() {
        return true;
    }
    public boolean cameraDebugOn() {
        return false;
    }
    public boolean debugCameraMoveLeft() {
        return false;
    }
    public boolean debugCameraMoveRight() {
        return false;
    }
    public boolean debugCameraMoveUp() {
        return false;
    }
    public boolean debugCameraMoveDown() {
        return false;
    }
    public Direction getPlayerDesiredDirection() {
        return playerDesiredDirection;
    }

    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        if(up.contains(screenX,screenY))
            playerDesiredDirection = Direction.UP;
        if(down.contains(screenX,screenY))
            playerDesiredDirection = Direction.DOWN;
        if(left.contains(screenX,screenY))
            playerDesiredDirection = Direction.LEFT;
        if(right.contains(screenX,screenY))
            playerDesiredDirection = Direction.RIGHT;
        return false;
    }

    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        playerDesiredDirection = null;
        return false;
    }

}
