package com.test.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
/*
    ///////////////////////////////////////////
    /// Constants Textures                  ///
    ///////////////////////////////////////////

    public static final String TEXTURE_ATLAS = "images/TankAtlas.atlas";

    public static final String LIGHT_TANK0 = "LightTank0";
    public static final String LIGHT_TANK22 = "LightTank22";
    public static final String LIGHT_TANK45 = "LightTank45";
    public static final String LIGHT_TANK78 = "LightTank78";
    public static final String LIGHT_TANK90 = "LightTank90";
    public static final String LIGHT_TANK112 = "LightTank112";
    public static final String LIGHT_TANK135 = "LightTank135";
    public static final String LIGHT_TANK158 = "LightTank158";
    public static final String LIGHT_TANK180 = "LightTank180";
    public static final String LIGHT_TANK202 = "LightTank202";
    public static final String LIGHT_TANK225 = "LightTank225";
    public static final String LIGHT_TANK248 = "LightTank248";
    public static final String LIGHT_TANK270 = "LightTank270";
    public static final String LIGHT_TANK292 = "LightTank292";
    public static final String LIGHT_TANK315 = "LightTank315";
    public static final String LIGHT_TANK338 = "LightTank338";

    public static final String HEAVY_TANK0 = "HeavyTank0";
    public static final String HEAVY_TANK22 = "HeavyTank22";
    public static final String HEAVY_TANK45 = "HeavyTank45";
    public static final String HEAVY_TANK78 = "HeavyTank78";
    public static final String HEAVY_TANK90 = "HeavyTank90";
    public static final String HEAVY_TANK112 = "HeavyTank112";
    public static final String HEAVY_TANK135 = "HeavyTank135";
    public static final String HEAVY_TANK158 = "HeavyTank158";
    public static final String HEAVY_TANK180 = "HeavyTank180";
    public static final String HEAVY_TANK202 = "HeavyTank202";
    public static final String HEAVY_TANK225 = "HeavyTank225";
    public static final String HEAVY_TANK248 = "HeavyTank248";
    public static final String HEAVY_TANK270 = "HeavyTank270";
    public static final String HEAVY_TANK292 = "HeavyTank292";
    public static final String HEAVY_TANK315 = "HeavyTank315";
    public static final String HEAVY_TANK338 = "HeavyTank338";

    ///////////////////////////////////////////
    /// END CONSTANTS                       ///
    ///////////////////////////////////////////*/
    
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    public LightTankAssets lightTankAssets;
    public HeavyTankAssets heavyTankAssets;
    public InterfaceAssets interfaceAssets;
    //public OnScreenControlsAssets onScreenControlsAssets;
    //public ButtonAssets buttonAssets;

    public AssetManager assetManager;



    //////////////////////////////////////////////////////
    /// обращаться напрямую к инстанс плохо.
    /// поэтому пусть каждый скрин получает доступ
    /// к единственному объекту ассетов через getInstance
    /// обсудим потом
    //////////////////////////////////////////////////////
/*
    private static Assets assetsInstance;
    public static synchronized Assets getInstance(AssetManager assetManager) {

        if (assetsInstance == null) {
            assetsInstance = new Assets(assetManager);
        }
        return assetsInstance;
    }

    private Assets() {
    }

    private Assets(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.Textures.BattleItems.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.Textures.BattleItems.TEXTURE_ATLAS);
        lightTankAssets = new LightTankAssets(atlas);
        heavyTankAssets = new HeavyTankAssets(atlas);
    }*/


    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);

        assetManager.load(Constants.Textures.BattleItems.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();
        assetManager.load(Constants.Textures.InterfaceItems.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();
        //assetManager.load("skins/uiskin.atlas", TextureAtlas.class);
        //assetManager.finishLoading();
        TextureAtlas atlasTsnks = assetManager.get(Constants.Textures.BattleItems.TEXTURE_ATLAS);
        lightTankAssets = new LightTankAssets(atlasTsnks);
        heavyTankAssets = new HeavyTankAssets(atlasTsnks);

        TextureAtlas atlasInterface = assetManager.get(Constants.Textures.InterfaceItems.TEXTURE_ATLAS);
        interfaceAssets = new InterfaceAssets(atlasInterface);

        assetManager.finishLoading();
    }
    //public AssetManager getAssetManager() {
    //    return assetManager;
    //}

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
            rotationRegions[0] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK0);
            rotationRegions[1] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK22);
            rotationRegions[2] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK45);
            rotationRegions[3] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK78);
            rotationRegions[4] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK90);
            rotationRegions[5] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK112);
            rotationRegions[6] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK135);
            rotationRegions[7] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK158);
            rotationRegions[8] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK180);
            rotationRegions[9] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK202);
            rotationRegions[10] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK225);
            rotationRegions[11] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK248);
            rotationRegions[12] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK270);
            rotationRegions[13] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK292);
            rotationRegions[14] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK315);
            rotationRegions[15] = atlas.findRegion(Constants.Textures.BattleItems.LIGHT_TANK338);
        }
    }

    public class HeavyTankAssets{

        public final TextureAtlas.AtlasRegion[] rotationRegions;

        public HeavyTankAssets(TextureAtlas atlas) {
            rotationRegions = new TextureAtlas.AtlasRegion[16];
            rotationRegions[0] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK0);
            rotationRegions[1] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK22);
            rotationRegions[2] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK45);
            rotationRegions[3] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK78);
            rotationRegions[4] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK90);
            rotationRegions[5] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK112);
            rotationRegions[6] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK135);
            rotationRegions[7] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK158);
            rotationRegions[8] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK180);
            rotationRegions[9] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK202);
            rotationRegions[10] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK225);
            rotationRegions[11] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK248);
            rotationRegions[12] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK270);
            rotationRegions[13] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK292);
            rotationRegions[14] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK315);
            rotationRegions[15] = atlas.findRegion(Constants.Textures.BattleItems.HEAVY_TANK338);
        }
    }

    public class InterfaceAssets {
        public final TextureAtlas.AtlasRegion background;
        public final TextureAtlas.AtlasRegion logo;

        public InterfaceAssets(TextureAtlas atlas) {
            background = atlas.findRegion(Constants.Textures.InterfaceItems.BACKGROUND);
            logo = atlas.findRegion(Constants.Textures.InterfaceItems.LOGO);

        }
    }

/*
    public class OnScreenControlsAssets {
        //todo OnScreenControls
        public final TextureAtlas.AtlasRegion moveRight;
        public final TextureAtlas.AtlasRegion moveLeft;
        public final TextureAtlas.AtlasRegion moveRight;
        public final TextureAtlas.AtlasRegion moveLeft;
        public final TextureAtlas.AtlasRegion shoot;
        public final TextureAtlas.AtlasRegion jump;

        public OnScreenControlsAssets(TextureAtlas atlas) {
            moveRight = atlas.findRegion(MOVE_RIGHT_BUTTON);
            moveLeft = atlas.findRegion(MOVE_LEFT_BUTTON);
            shoot = atlas.findRegion(SHOOT_BUTTON);
            jump = atlas.findRegion(JUMP_BUTTON);
        }
    }*/

    //public class
}
