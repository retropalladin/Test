package com.test.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.test.game.Level;
import com.test.game.LevelInputManager;
import com.test.game.entities.NpcTank;

public class ChaseCamera {

    private Boolean following;
    private int horizontalShift;
    private int verticalShift;
    private float levelWidth;
    private float levelHeight;
    private float tmpDif;
    private float tmpSgn;
    private float viewportWidthH;
    private float viewportHeightH;
    private Vector2 playerTankPosition;
    private Vector2 cameraDestination;
    public OrthographicCamera camera;

    public ChaseCamera() {
        camera = new OrthographicCamera();
        camera.viewportHeight = Constants.Renderer.WORLD_VISIBLE_HEIGHT;
        viewportHeightH = Constants.Renderer.WORLD_VISIBLE_HEIGHT / 2;
        cameraDestination = new Vector2();
        following = true;
    }

    public void update(float delta, Level level) {
        if (Constants.Developing.DEBUG & LevelInputManager.instance.levelInput.cameraDebugOn()){
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
        if(level.playerTank == null){
            cameraDestination.x = level.allySpawnX * Constants.Physics.CELL_SIZE + NpcTank.TANK_WIDTH_H;
            cameraDestination.y = level.allySpawnY * Constants.Physics.CELL_SIZE + NpcTank.TANK_HEIGHT_H;
        }else {
            playerTankPosition = level.playerTank.body.getWorldCenter();
            cameraDestination.x = playerTankPosition.x;
            cameraDestination.y = playerTankPosition.y;
            switch (level.playerTank.direction) {
                case LEFT:
                    cameraDestination.x -= Constants.Physics.CELL_SIZE * horizontalShift;
                    break;
                case RIGHT:
                    cameraDestination.x += Constants.Physics.CELL_SIZE * horizontalShift;
                    break;
                case UP:
                    cameraDestination.y += Constants.Physics.CELL_SIZE * verticalShift;
                    break;
                case DOWN:
                    cameraDestination.y -= Constants.Physics.CELL_SIZE * verticalShift;
                    break;
            }
        }

        if(horizontalShift != 0)
            if (cameraDestination.x < viewportWidthH + Constants.Physics.CELL_SIZE)
                cameraDestination.x = viewportWidthH + Constants.Physics.CELL_SIZE;
            else if (cameraDestination.x > level.levelWidth - viewportWidthH + Constants.Physics.CELL_SIZE)
                cameraDestination.x = level.levelWidth - viewportWidthH + Constants.Physics.CELL_SIZE;
        if (verticalShift != 0)
            if( cameraDestination.y < viewportHeightH + Constants.Physics.CELL_SIZE)
                cameraDestination.y = viewportHeightH + Constants.Physics.CELL_SIZE;
            else if (cameraDestination.y > level.levelHeigt - camera.viewportHeight / 2 + Constants.Physics.CELL_SIZE)
                cameraDestination.y = level.levelHeigt - camera.viewportHeight / 2 + Constants.Physics.CELL_SIZE;
    }

    private void moveToDestination(float delta){
        tmpDif = cameraDestination.x - camera.position.x;
        tmpSgn = Math.signum(tmpDif);
        tmpDif = Math.abs(tmpDif);
        if(tmpDif < Constants.Renderer.CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED)
            if (tmpDif < Constants.Renderer.CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED * delta) {
                camera.position.x = cameraDestination.x;
            }else{
                camera.position.x += delta * tmpSgn * Constants.Renderer.CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED;
            }
        else
            camera.position.x += tmpDif * tmpSgn * delta;
        tmpDif = cameraDestination.y - camera.position.y;
        tmpSgn = Math.signum(tmpDif);
        tmpDif = Math.abs(tmpDif);
        if(tmpDif < Constants.Renderer.CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED)
            if (tmpDif < Constants.Renderer.CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED * delta) {
                camera.position.y = cameraDestination.y;
            }else{
                camera.position.y += delta * tmpSgn * Constants.Renderer.CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED;
            }
        else
            camera.position.y += tmpDif  * tmpSgn * delta;
    }

    private void debugMove(float delta){
        if (LevelInputManager.instance.levelInput.debugCameraMoveLeft()) {
            camera.position.x -= delta * Constants.Renderer.CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED;
        }
        if (LevelInputManager.instance.levelInput.debugCameraMoveRight()) {
            camera.position.x += delta * Constants.Renderer.CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED;
        }
        if (LevelInputManager.instance.levelInput.debugCameraMoveUp()) {
            camera.position.y += delta * Constants.Renderer.CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED;
        }
        if (LevelInputManager.instance.levelInput.debugCameraMoveDown()) {
            camera.position.y -= delta * Constants.Renderer.CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED;
        }
    }

    public void presetCameraPosition(Level level) {
        levelWidth = level.levelWidth;
        levelHeight = level.levelHeigt;
        camera.position.x = level.levelWidth/2;
        camera.position.y = level.levelHeigt/2;
        camera.update();
    }

    public void updateCameraResolution(int width, int height) {
        if(levelHeight < Constants.Renderer.WORLD_VISIBLE_HEIGHT) {
            verticalShift = 0;
        } else {
            verticalShift = Constants.Renderer.CHASE_CAMERA_VERTICAL_CELLS_SHIFT;
        }
        camera.viewportWidth = Constants.Renderer.WORLD_VISIBLE_HEIGHT * 1.0f * width / height;
        viewportWidthH = camera.viewportWidth / 2;
        if(levelWidth < camera.viewportWidth){
            horizontalShift = 0;
        } else {
            horizontalShift = Constants.Renderer.CHASE_CAMERA_HORIZONTAL_CELLS_SHIFT;
        }
        camera.update();
    }
}
