package com.test.game.utils;

public class Constants {
    private Constants() {}

    public final class Developing {
        private Developing() {
        }

        public static final boolean DEBUG = true;
        public static final boolean DEBUG_PHYSICS_RENDER = true;
    }
    public final class Settings {
        private Settings() {
        }

        public static final int GOD_DAMAGE = 100000;
        public static final float PLAYER_RELOAD_MUL = 1.25f;
        public static final float FRAME_TIME_MAX = 0.25f;
    }

    public final class Renderer {
        private Renderer() {
        }

        public static final int CHASE_CAMERA_HORIZONTAL_CELLS_SHIFT = 2;
        public static final int CHASE_CAMERA_VERTICAL_CELLS_SHIFT = 2;
        public static final float WORLD_VISIBLE_HEIGHT = 32;
        public static final float CHASE_CAMERA_AUTO_FOLLOWING_MOVE_SPEED = 7f;
        public static final float CHASE_CAMERA_NOT_FOLLOWING_MOVE_SPEED = 10.0f;
        public static final float ROTATE_SECTOR = 22.5f;
        public static final float ROTATE_SECTOR_H = ROTATE_SECTOR * 0.5f;
    }

    public final class Physics {
        private Physics() {
        }

        public static final int VELOCITY_ITERATIONS = 6;
        public static final int POSITION_ITERATIONS = 2;
        public static final float PHYSICS_STEP = 1 / 300f;
        public static final float CELL_SIZE = 2;
        public static final float CELL_SIZE_H = CELL_SIZE * 0.5f;

        public static final short LAND_GROUND = 1;
        public static final short LAND_SAND = (1 << 2);

        public static final short CATEGORY_EMPTY = 1;
        public static final short CATEGORY_WALL = (1 << 2);
        public static final short CATEGORY_ALLY_TANK = (1 << 3);
        public static final short CATEGORY_ENEMY_TANK = (1 << 4);
        public static final short CATEGORY_TANK_ON_MOVE = (1 << 5);
        public static final short CATEGORY_SPAWN = (1 << 6);
        public static final short CATEGORY_ALLY_BULLET = (1 << 13);
        public static final short CATEGORY_ENEMY_BULLET = (1 << 14);

        public static final short MASK_WALL = CATEGORY_ALLY_TANK | CATEGORY_ENEMY_TANK | CATEGORY_ALLY_BULLET | CATEGORY_ENEMY_BULLET;
        public static final short MASK_ALLY_TANK = CATEGORY_WALL | CATEGORY_ALLY_TANK | CATEGORY_ENEMY_TANK | CATEGORY_ENEMY_BULLET;
        public static final short MASK_ENEMY_TANK = CATEGORY_WALL | CATEGORY_ALLY_TANK | CATEGORY_ENEMY_TANK | CATEGORY_ALLY_BULLET;
        public static final short MASK_ALLY_BULLET = CATEGORY_WALL | CATEGORY_ENEMY_TANK | CATEGORY_ENEMY_BULLET;
        public static final short MASK_ENEMY_BULLET = CATEGORY_WALL | CATEGORY_ALLY_TANK | CATEGORY_ALLY_BULLET;

        public static final short PLAYER_TANK_MOVE_MASK = CATEGORY_EMPTY | CATEGORY_SPAWN;
    }

    public final class Sounds {
        private Sounds(){
        }
    }

    public final class Textures {
        private Textures() {
        }

        public final class BattleItems {
            private BattleItems() {
            }

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
        }

        public final class InterfaceItems {
            private InterfaceItems() {
            }

            public static final String TEXTURE_ATLAS = "images/interface_atlas.atlas";

            public static final String MENU_BACKGROUND = "menu_backgr";
        }

        public final class DecorationItems {
            private DecorationItems() {

            }
        }
    }

    public final class Screens {
        private Screens() {
        }

        public static final int INTRODUCTION_SCREEN = 0;
        public static final int MAINMENU_SCREEN = 1;
        public static final int GAMEPLAY_SCREEN = 2;
    }
}
