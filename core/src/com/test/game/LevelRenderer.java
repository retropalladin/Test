package com.test.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.test.game.utils.Assets;
import com.test.game.utils.ChaseCamera;
import com.test.game.utils.Constants;

public class LevelRenderer {

    private ChaseCamera chaseCamera;
    private Box2DDebugRenderer debugRenderer;
    private TextureAtlas.AtlasRegion currentRegion;
    private float rotatePositionTmp;

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
        if(level.playerTank != null)
           drawPlayerTank(level, batch);
        batch.end();
    }

    public void drawPlayerTank(Level level, SpriteBatch batch){
        rotatePositionTmp = level.playerTank.getRotatePosition() + Constants.ROTATE_SECTOR_H;
        if(rotatePositionTmp >= 360)
            rotatePositionTmp -=360;
        switch (level.playerTank.tankType){
            case LIGHT_TANK:
                currentRegion = Assets.instance.lightTankAssets.rotationRegions[(int)(rotatePositionTmp/Constants.ROTATE_SECTOR)];
                break;
            case HEAVY_TANK:
                currentRegion = Assets.instance.heavyTankAssets.rotationRegions[(int)(rotatePositionTmp/Constants.ROTATE_SECTOR)];
                break;
        }
        batch.draw(currentRegion.getTexture(), level.playerTank.getBody().getPosition().x, level.playerTank.getBody().getPosition().y,
                0, 0, Constants.TANK_WIDTH, Constants.TANK_HEIGHT,
                1, 1, 0, currentRegion.getRegionX(), currentRegion.getRegionY(),
                currentRegion.getRegionWidth(), currentRegion.getRegionHeight(),
                false, false);
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
