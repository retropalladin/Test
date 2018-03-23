package com.test.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.test.game.Level;

public class ChaseCamera {

    public OrthographicCamera camera;
    private Vector2 playerTankPosition;
    private Boolean following;

    public ChaseCamera() {
        camera = new OrthographicCamera();
        following = true;
    }

    public void updateCameraResolution(int width, int height) {
        camera.viewportHeight = Constants.WORLD_VISIBLE_HEIGHT;
        camera.viewportWidth = Constants.WORLD_VISIBLE_HEIGHT * 1.0f * width / height;
    }

    public void update(float delta, Level level) {

        if (Constants.DEBUG & Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            following = !following;
        }
        if (following) {
            playerTankPosition = level.playerTank.body.getWorldCenter();
            camera.position.x = playerTankPosition.x;
            camera.position.y = playerTankPosition.y;
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
}
