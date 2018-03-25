package com.test.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.test.game.utils.Assets;
import com.test.game.utils.ChaseCamera;
import com.test.game.utils.Constants;
import com.test.game.utils.Utils;

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
        TextureAtlas.AtlasRegion region = Assets.instance.lightTankAssets.rotationRegions[(int)(level.playerTank.getRotatePosition()/22.5)];
        batch.draw(
                region.getTexture(),
                level.playerTank.getBody().getPosition().x,
                level.playerTank.getBody().getPosition().y,
                0,
                0,
                Constants.TANK_WIDTH,
                Constants.TANK_HEIGHT,
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
        batch.end();
    }

    public void presetCameraPosition(Level level) {
        chaseCamera.presetCameraPosition(level);
    }

    public void updateCameraResolution(int width, int height) {
        chaseCamera.updateCameraResolution(width,height);
    }

    public void dispose(){
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer.dispose();
     }
}
