package com.test.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.test.game.Level;
import com.test.game.LevelInputManager;

public class ChaseCamera {

    private Boolean following;
    private float tmpDif;
    private float tmpSgn;
    private float viewportWidthH;
    private float viewportHeightH;
    private Vector2 playerTankPosition;
    private Vector2 cameraDestination;
    public OrthographicCamera camera;

    public ChaseCamera() {
        camera = new OrthographicCamera();
        camera.viewportHeight = Constants.WORLD_VISIBLE_HEIGHT;
        viewportHeightH = Constants.WORLD_VISIBLE_HEIGHT / 2;
        cameraDestination = new Vector2();
        following = true;
    }

    public void update(float delta, Level level) {
        if (Constants.DEBUG & LevelInputManager.instance.levelInput.cameraDebugOn()){
            following = !following;
        }
        if (following) {
            recalculateDestination(level);
            moveToDestination(delta);
        } else {
            debugMove(delta);
        }
        camera.update();
    }

    private void recalculateDestination(Level level) {
        playerTankPosition = level.playerTank.body.getWorldCenter();
        cameraDestination.x = playerTankPosition.x;
        cameraDestination.y = playerTankPosition.y;
        switch(level.playerTank.direction) {
            case LEFT:
                cameraDestination.x -= Constants.CELL_SIZE * Constants.CHASE_CAMERA_HORIZONTAL_CELLS_SHIFT;
                break;
            case RIGHT:
                cameraDestination.x += Constants.CELL_SIZE * Constants.CHASE_CAMERA_HORIZONTAL_CELLS_SHIFT;
                break;
            case UP:
                cameraDestination.y += Constants.CELL_SIZE * Constants.CHASE_CAMERA_VERTICAL_CELLS_SHIFT;
                break;
            case DOWN:
                cameraDestination.y -= Constants.CELL_SIZE * Constants.CHASE_CAMERA_VERTICAL_CELLS_SHIFT;
                break;
        }
        if(cameraDestination.x < viewportWidthH + Constants.CELL_SIZE)
            cameraDestination.x = viewportWidthH + Constants.CELL_SIZE;
        else
        if(cameraDestination.x > level.levelWidth - viewportWidthH - Constants.CELL_SIZE)
            cameraDestination.x = level.levelWidth - viewportWidthH - Constants.CELL_SIZE;
        if(cameraDestination.y < viewportHeightH + Constants.CELL_SIZE)
            cameraDestination.y = viewportHeightH + Constants.CELL_SIZE;
        else
        if(cameraDestination.y > level.levelHeigt - camera.viewportHeight/2 - Constants.CELL_SIZE)
            cameraDestination.y = level.levelHeigt - camera.viewportHeight/2 - Constants.CELL_SIZE;

    }

    private void moveToDestination(float delta){
        tmpDif = cameraDestination.x - camera.position.x;
        tmpSgn = Math.signum(tmpDif);
        tmpDif = Math.abs(tmpDif);
        if(tmpDif < Constants.CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED)
            if (tmpDif < Constants.CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED * delta) {
                camera.position.x = cameraDestination.x;
            }else{
                camera.position.x += delta * tmpSgn * Constants.CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED;
            }
        else
            camera.position.x += tmpDif * tmpSgn * delta;
        tmpDif = cameraDestination.y - camera.position.y;
        tmpSgn = Math.signum(tmpDif);
        tmpDif = Math.abs(tmpDif);
        if(tmpDif < Constants.CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED)
            if (tmpDif < Constants.CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED * delta) {
                camera.position.y = cameraDestination.y;
            }else{
                camera.position.y += delta * tmpSgn * Constants.CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED;
            }
        else
            camera.position.y += tmpDif  * tmpSgn * delta;
    }

    private void debugMove(float delta){
        if (LevelInputManager.instance.levelInput.debugCameraMoveLeft()) {
            camera.position.x -= delta * Constants.CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED;
        }
        if (LevelInputManager.instance.levelInput.debugCameraMoveRight()) {
            camera.position.x += delta * Constants.CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED;
        }
        if (LevelInputManager.instance.levelInput.debugCameraMoveUp()) {
            camera.position.y += delta * Constants.CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED;
        }
        if (LevelInputManager.instance.levelInput.debugCameraMoveDown()) {
            camera.position.y -= delta * Constants.CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED;
        }
    }

    public void presetCameraPosition(Level level) {
        camera.position.x = level.levelWidth/2;
        camera.position.y = level.levelHeigt/2;
        camera.update();
    }

    public void updateCameraResolution(int width, int height) {
        camera.viewportWidth = Constants.WORLD_VISIBLE_HEIGHT * 1.0f * width / height;
        viewportWidthH = camera.viewportWidth / 2;
        camera.update();
    }
}
