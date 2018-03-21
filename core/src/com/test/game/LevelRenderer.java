package com.test.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.test.game.utils.Constants;

public class LevelRenderer {

    private ExtendViewport viewport;
    private Box2DDebugRenderer debugRenderer;

    public LevelRenderer() {
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer = new Box2DDebugRenderer();
    }

    public void render(Level level, SpriteBatch batch) {
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer.render(level.world, viewport.getCamera().combined);
    }

    public void updateViewport(int width, int height) {
        //orthographicCamera.viewportWidth = width;
        //orthographicCamera.viewportHeight = height;
        //orthographicCamera.viewportWidth = worldLogic.CELL_SIZE * CELL_V_VISIBLE * width * 1.0f / height;
    }

    public void dispose(){
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer.dispose();
     }
}
