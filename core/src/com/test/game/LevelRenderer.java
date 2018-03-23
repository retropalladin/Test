package com.test.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.test.game.utils.ChaseCamera;
import com.test.game.utils.Constants;

public class LevelRenderer {

    private ChaseCamera chaseCamera;
    private Box2DDebugRenderer debugRenderer;

    public LevelRenderer() {
        chaseCamera = new ChaseCamera();
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer = new Box2DDebugRenderer();
    }

    public void render(float delta, Level level, SpriteBatch batch) {
        chaseCamera.update(delta, level);
        if(Constants.DEBUG_PHYSICS_RENDER) {
            debugRenderer.render(level.world, chaseCamera.camera.combined);
        }
        batch.setProjectionMatrix(chaseCamera.camera.combined);
        batch.begin();
        batch.end();
    }

    public void updateCameraResolution(int width, int height) {
        chaseCamera.updateCameraResolution(width,height);
    }

    public void dispose(){
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer.dispose();
     }
}
