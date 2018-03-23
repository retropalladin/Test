package com.test.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.test.game.Level;

public class ChaseCamera {

    public OrthographicCamera camera;
    private Vector2 playerTankPosition;
    private Vector2 cameraPosition;
    private Vector2 cameraDestination;
    private Boolean following;
    private float tmpDif;
    private float tmpSgn;

    public ChaseCamera() {
        camera = new OrthographicCamera();
        cameraDestination = new Vector2();
        cameraPosition = new Vector2();
        following = true;
    }

    public void update(float delta, Level level) {
        if (Constants.DEBUG & Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            following = !following;
        }
        if (following) {
            recalculateCameraDestination(level);
            recalculateCameraPosition(delta);
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                camera.position.x -= delta * Constants.CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                camera.position.x += delta * Constants.CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                camera.position.y += delta * Constants.CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                camera.position.y -= delta * Constants.CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED;
            }
        }
        camera.update();
    }

    private void recalculateCameraDestination(Level level) {
        cameraPosition.x = camera.position.x;
        cameraPosition.y = camera.position.y;
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
        if(cameraDestination.x < camera.viewportWidth/2 + Constants.CELL_SIZE)
            cameraDestination.x = camera.viewportWidth/2 + Constants.CELL_SIZE;
        else
        if(cameraDestination.x > level.levelWidth - camera.viewportWidth/2 - Constants.CELL_SIZE)
            cameraDestination.x = level.levelWidth - camera.viewportWidth/2 - Constants.CELL_SIZE;
        if(cameraDestination.y < camera.viewportHeight/2 + Constants.CELL_SIZE)
            cameraDestination.y = camera.viewportHeight/2 + Constants.CELL_SIZE;
        else
        if(cameraDestination.y > level.levelHeigt - camera.viewportHeight/2 - Constants.CELL_SIZE)
            cameraDestination.y = level.levelHeigt - camera.viewportHeight/2 - Constants.CELL_SIZE;

    }

    private void recalculateCameraPosition(float delta){
        tmpDif = cameraDestination.x - cameraPosition.x;
        tmpSgn = Math.signum(tmpDif);
        tmpDif = Math.abs(tmpDif);
        if(tmpDif < Constants.CHASE_CAMERA_FOLLOWING_MOVE_SPEED)
            if (tmpDif < Constants.CHASE_CAMERA_FOLLOWING_MOVE_SPEED * delta) {
                camera.position.x = cameraDestination.x;
            }else{
                camera.position.x += delta * tmpSgn * Constants.CHASE_CAMERA_FOLLOWING_MOVE_SPEED;
            }
        else
            camera.position.x += tmpDif * tmpSgn * delta;
        tmpDif = cameraDestination.y - cameraPosition.y;
        tmpSgn = Math.signum(tmpDif);
        tmpDif = Math.abs(tmpDif);
        if(tmpDif < Constants.CHASE_CAMERA_FOLLOWING_MOVE_SPEED)
            if (tmpDif < Constants.CHASE_CAMERA_FOLLOWING_MOVE_SPEED * delta) {
                camera.position.y = cameraDestination.y;
            }else{
                camera.position.y += delta * tmpSgn * Constants.CHASE_CAMERA_FOLLOWING_MOVE_SPEED;
            }
        else
            camera.position.y += tmpDif  * tmpSgn * delta;
    }

    public void presetCameraPosition(Level level) {
        camera.position.x = level.levelWidth/2;
        camera.position.y = level.levelHeigt/2;
        camera.update();
    }

    public void updateCameraResolution(int width, int height) {
        camera.viewportHeight = Constants.WORLD_VISIBLE_HEIGHT;
        camera.viewportWidth = Constants.WORLD_VISIBLE_HEIGHT * 1.0f * width / height;
        camera.update();
    }
}
