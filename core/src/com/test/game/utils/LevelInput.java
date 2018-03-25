package com.test.game.utils;

import com.badlogic.gdx.InputProcessor;
import com.test.game.utils.Enums.Direction;

public interface LevelInput extends InputProcessor{
    boolean debugCameraMoveLeft();
    boolean debugCameraMoveRight();
    boolean debugCameraMoveUp();
    boolean debugCameraMoveDown();
    Direction getPlayerDesiredDirectiond();
}
