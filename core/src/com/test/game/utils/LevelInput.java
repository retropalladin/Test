package com.test.game.utils;

import com.badlogic.gdx.InputProcessor;

public interface LevelInput extends InputProcessor{
    public boolean debugCameraMoveLeft();
    public boolean debugCameraMoveRight();
    public boolean debugCameraMoveUp();
    public boolean debugCameraMoveDown();
    public Enums.Direction getPlayerMoveDirection();
}
