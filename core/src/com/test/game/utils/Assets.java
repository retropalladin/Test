package com.test.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    public LightTankAssets lightTankAssets;
    public HeavyTankAssets heavyTankAssets;

    private AssetManager assetManager;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        lightTankAssets = new LightTankAssets(atlas);
        heavyTankAssets = new HeavyTankAssets(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class LightTankAssets{

        public final TextureAtlas.AtlasRegion[] rotationRegions;

        public LightTankAssets(TextureAtlas atlas) {
            rotationRegions = new TextureAtlas.AtlasRegion[16];
            rotationRegions[0] = atlas.findRegion(Constants.LIGHT_TANK0);
            rotationRegions[1] = atlas.findRegion(Constants.LIGHT_TANK22);
            rotationRegions[2] = atlas.findRegion(Constants.LIGHT_TANK45);
            rotationRegions[3] = atlas.findRegion(Constants.LIGHT_TANK78);
            rotationRegions[4] = atlas.findRegion(Constants.LIGHT_TANK90);
            rotationRegions[5] = atlas.findRegion(Constants.LIGHT_TANK112);
            rotationRegions[6] = atlas.findRegion(Constants.LIGHT_TANK135);
            rotationRegions[7] = atlas.findRegion(Constants.LIGHT_TANK158);
            rotationRegions[8] = atlas.findRegion(Constants.LIGHT_TANK180);
            rotationRegions[9] = atlas.findRegion(Constants.LIGHT_TANK202);
            rotationRegions[10] = atlas.findRegion(Constants.LIGHT_TANK225);
            rotationRegions[11] = atlas.findRegion(Constants.LIGHT_TANK248);
            rotationRegions[12] = atlas.findRegion(Constants.LIGHT_TANK270);
            rotationRegions[13] = atlas.findRegion(Constants.LIGHT_TANK292);
            rotationRegions[14] = atlas.findRegion(Constants.LIGHT_TANK315);
            rotationRegions[15] = atlas.findRegion(Constants.LIGHT_TANK338);
        }
    }

    public class HeavyTankAssets{

        public final TextureAtlas.AtlasRegion[] rotationRegions;

        public HeavyTankAssets(TextureAtlas atlas) {
            rotationRegions = new TextureAtlas.AtlasRegion[16];
            rotationRegions[0] = atlas.findRegion(Constants.HEAVY_TANK0);
            rotationRegions[1] = atlas.findRegion(Constants.HEAVY_TANK22);
            rotationRegions[2] = atlas.findRegion(Constants.HEAVY_TANK45);
            rotationRegions[3] = atlas.findRegion(Constants.HEAVY_TANK78);
            rotationRegions[4] = atlas.findRegion(Constants.HEAVY_TANK90);
            rotationRegions[5] = atlas.findRegion(Constants.HEAVY_TANK112);
            rotationRegions[6] = atlas.findRegion(Constants.HEAVY_TANK135);
            rotationRegions[7] = atlas.findRegion(Constants.HEAVY_TANK158);
            rotationRegions[8] = atlas.findRegion(Constants.HEAVY_TANK180);
            rotationRegions[9] = atlas.findRegion(Constants.HEAVY_TANK202);
            rotationRegions[10] = atlas.findRegion(Constants.HEAVY_TANK225);
            rotationRegions[11] = atlas.findRegion(Constants.HEAVY_TANK248);
            rotationRegions[12] = atlas.findRegion(Constants.HEAVY_TANK270);
            rotationRegions[13] = atlas.findRegion(Constants.HEAVY_TANK292);
            rotationRegions[14] = atlas.findRegion(Constants.HEAVY_TANK315);
            rotationRegions[15] = atlas.findRegion(Constants.HEAVY_TANK338);
        }
    }
}
