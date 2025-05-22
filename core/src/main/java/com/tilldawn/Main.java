package com.tilldawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.control.SignUpController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.SaveUserToJson;
import com.tilldawn.view.MainMenuView;
import com.tilldawn.view.SignUpMenuView;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private static SpriteBatch batch;
    private Texture image;
    private static Main main;
    private static App app;


    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        app = new App();
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        SaveUserToJson.readFile();


        getMain().setScreen(new SignUpMenuView(new SignUpController(), GameAssetManager.getGameAssetManager().getSkin()));


    }

    @Override
    public void render() {
        super.render();


    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static Main getMain() {
        return main;
    }

    public static App getApp() {
        return app;
    }


}
