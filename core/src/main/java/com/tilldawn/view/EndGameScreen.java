package com.tilldawn.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.tilldawn.Main;
import com.tilldawn.model.User;

public class EndGameScreen implements Screen {

    private Main game;
    private User player;
    private SpriteBatch batch;
    private BitmapFont font;

    private boolean isPlayerDead;
    private float survivalTime;
    private int kills;
    private int score;

    public EndGameScreen(User player, boolean isPlayerDead) {

        this.player = player;
        this.isPlayerDead = isPlayerDead;
        this.batch = new SpriteBatch();

        this.font = new BitmapFont();
        font.getData().setScale(2f);
        font.setColor(Color.WHITE);

        this.survivalTime = player.getTime();
        this.kills = player.getElimination();
        this.score = (int) (survivalTime * kills);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        float centerX = Gdx.graphics.getWidth() / 2f;
        float startY = Gdx.graphics.getHeight() / 2f + 150;

        font.draw(batch, "Game Over", centerX - 100, startY);
        font.draw(batch, "Username: " + player.getUsername(), centerX - 100, startY - 50);
        font.draw(batch, "Survival Time: " + (int) survivalTime + " seconds", centerX - 100, startY - 100);
        font.draw(batch, "Kills: " + kills, centerX - 100, startY - 150);
        font.draw(batch, "Score: " + score, centerX - 100, startY - 200);

        if (isPlayerDead) {
            font.setColor(Color.RED);
            font.draw(batch, "DEAD", centerX - 50, startY - 250);
        } else {
            font.setColor(Color.GREEN);
            font.draw(batch, "WIN", centerX - 50, startY - 250);
        }

        font.setColor(Color.WHITE);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
