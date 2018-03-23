package com.test.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.test.game.utils.Constants;

public class LevelRenderer {

    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;

    public LevelRenderer() {
        camera = new OrthographicCamera();
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer = new Box2DDebugRenderer();
    }

    public void render(Level level, SpriteBatch batch) {
        updateCamera(level);
        camera.update();
        if(Constants.DEBUG_PHYSICS_RENDER) {
            debugRenderer.render(level.world, camera.combined);
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();
    }

    public void updateCamera(Level level){

    }

    public void updateCameraResolution(int width, int height) {
        camera.viewportHeight = Constants.WORLD_VISIBLE_HEIGHT;
        camera.viewportWidth = Constants.WORLD_VISIBLE_HEIGHT * 1.0f * width / height;
    }

    public void dispose(){
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer.dispose();
     }
}