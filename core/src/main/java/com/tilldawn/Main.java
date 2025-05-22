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
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.MainMenuView;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private static SpriteBatch batch;
    private Texture image;
    private static Main main;
    private int x = 0;
    private int y = 0;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
//        image = new Texture("libgdx.png");
        getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));


    }

    @Override
    public void render() {
        super.render();
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//        batch.begin();
//        batch.draw(image, x, y);
//        batch.end();
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            y += 3;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            y -= 3;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            x += 3;
//        }
//        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            x -= 3;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
//            x += 2;
//            y += 2;
//        }

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
}
