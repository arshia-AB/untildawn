package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.model.SaveUserToJson;
import com.tilldawn.model.User;
import com.tilldawn.model.GameAssetManager;

public class EndGameScreen implements Screen {

    private Main game;
    private User player;
    private SpriteBatch batch;
    private BitmapFont font;

    private boolean isPlayerDead;
    private float survivalTime;
    private int kills;
    private int score;

    private Stage stage;
    private Skin skin;
    private Texture bgTexture;

    public EndGameScreen(User player, boolean isPlayerDead) {
        this.player = player;
        this.isPlayerDead = isPlayerDead;

        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        font.getData().setScale(2f);
        font.setColor(Color.WHITE);

        this.survivalTime = player.getSurvivalTime();
        this.kills = player.getElimination();
        this.score = player.getScore() + (int) (survivalTime * kills);
        player.setScore(score);
        SaveUserToJson.saveUserToJson(player);


        this.skin = GameAssetManager.getGameAssetManager().getSkin();
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.bgTexture = new Texture("backgrounds/33.png");
    }

    @Override
    public void show() {
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        Table table = new Table();
        table.setFillParent(true);
        table.bottom().padBottom(50);
        stage.addActor(table);

        TextButton enterButton = new TextButton("Enter (Back to Main Menu)", skin);
        enterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToMainMenu();
            }
        });

        table.add(enterButton).center();
    }

    private void goToMainMenu() {
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        batch.begin();
        float centerX = Gdx.graphics.getWidth() / 2f;
        float startY = Gdx.graphics.getHeight() / 2f + 150;

        font.draw(batch, "Game Over", centerX - 100, startY);
        font.draw(batch, "Username: " + player.getUsername(), centerX - 100, startY - 50);
        font.draw(batch, "Survival Time: " + (int) survivalTime + " seconds", centerX - 100, startY - 100);
        font.draw(batch, "Kills: " + kills, centerX - 100, startY - 150);
        font.draw(batch, "Score: " + score, centerX - 100, startY - 200);
        player.setScore(score);
        font.setColor(isPlayerDead ? Color.RED : Color.GREEN);
        font.draw(batch, isPlayerDead ? "DEAD" : "WIN", centerX - 50, startY - 250);
        font.setColor(Color.WHITE);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            goToMainMenu();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        bgTexture.dispose();
    }
}
