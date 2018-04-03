package com.test.game.level.endCheckers;

import com.test.game.level.Level;
import com.test.game.player.PlayerManager;
import com.test.game.utils.LevelEndChecker;


public class LevelEndCheckerKAorDie implements LevelEndChecker {
    @Override
    public boolean checkWinCondition(Level level) {
        return false;
    }

    @Override
    public boolean checkFailCondition(Level level) {
        if(level.deadPlayerTank != null && level.deadPlayerTank.playerManager.stats[PlayerManager.LIVES_ID] < 0)
            return true;
        return false;
    }
}
