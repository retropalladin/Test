package com.test.game.utils;

import com.test.game.level.Level;

public interface LevelEndChecker {
    boolean checkWinCondition(Level level);
    boolean checkFailCondition(Level level);
}
