package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.model.User;

public class CheatCodeScreen extends ScreenAdapter {

    private Stage stage;
    private Skin skin;
    private GameView gameView;

    public CheatCodeScreen(GameView gameview) {
        this.gameView = gameview;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        Texture bgTexture = new Texture(Gdx.files.internal("backgrounds/15.png"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label title = new Label("Cheat Codes", skin);
        title.setFontScale(2);
        table.add(title).colspan(1).pad(20);
        table.row();

        User user = Main.getApp().getCurrentUser();


        TextButton reduceTimeBtn = new TextButton("⏳ decrease game time", skin);
        reduceTimeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int newTime = Math.max(0, user.getGameTime() - 60);
                gameView.setMaxGameTime(newTime);
            }
        });


        TextButton levelUpBtn = new TextButton("🆙 level up", skin);
        levelUpBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                user.increaseLevel();


            }
        });


        TextButton addHealthBtn = new TextButton("❤️ increase HP", skin);
        addHealthBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (user.getPlayerHP() !=user.getHero().getHP()) {
                    user.setPlayerHP(user.getHero().getHP());
                }
            }
        });

        TextButton bossFightBtn = new TextButton("💀 go to boss fight", skin);
        bossFightBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(gameView);
            }
        });

        TextButton addScoreBtn = new TextButton("⭐ get score", skin);
        addScoreBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                user.setScore(user.getScore() + 500);
            }
        });


        TextButton backBtn = new TextButton("⬅️ back", skin);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(gameView);
            }
        });


        table.add(reduceTimeBtn).width(500).pad(10);
        table.row();
        table.add(levelUpBtn).width(500).pad(10);
        table.row();
        table.add(addHealthBtn).width(500).pad(10);
        table.row();
        table.add(bossFightBtn).width(500).pad(10);
        table.row();
        table.add(addScoreBtn).width(500).pad(10);
        table.row().padTop(30);
        table.add(backBtn).width(300).pad(10);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
