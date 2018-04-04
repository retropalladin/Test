package com.test.game.inputs;

import com.badlogic.gdx.InputProcessor;
import com.test.game.utils.Enums.Direction;

public interface GameplayInput extends InputProcessor{
    boolean shoot();
    boolean cameraDebugOn();
    boolean debugCameraMoveLeft();
    boolean debugCameraMoveRight();
    boolean debugCameraMoveUp();
    boolean debugCameraMoveDown();
    Direction getPlayerDesiredDirection();
}
