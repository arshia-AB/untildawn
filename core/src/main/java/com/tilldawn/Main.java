package com.tilldawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private static SpriteBatch batch;
    private Texture image;
    private static Main main;
    private int x = 0;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, x, 210);
        batch.end();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            x += 3;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
