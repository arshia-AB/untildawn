package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreboardScreen extends ScreenAdapter {
    private Stage stage;
    private Skin skin;

    public ScoreboardScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Texture bgTexture = new Texture(Gdx.files.internal("backgrounds/25.png"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top().padTop(30);
        stage.addActor(mainTable);

        Label title = new Label("🏆 Top 10 Players", skin);
        title.setFontScale(2f);
        title.setAlignment(Align.center);
        mainTable.add(title).colspan(5).padBottom(20);
        mainTable.row();

        Table table = new Table();
        table.defaults().pad(6).padLeft(15).padRight(15);

        table.add(new Label("Rank", skin)).pad(50).left();
        table.add(new Label("Username", skin)).pad(50).left();
        table.add(new Label("Score", skin)).pad(50).left();
        table.add(new Label("Kills", skin)).pad(50).left();
        table.add(new Label("Survival Time", skin)).pad(50).left();
        table.row();

        List<User> users = new ArrayList<>(Main.getApp().getAllUsers().values());
        users.sort(Comparator.comparingInt(User::getScore).reversed());
        List<User> topUsers = users.subList(0, Math.min(10, users.size()));
        String currentUsername = Main.getApp().getCurrentUser().getUsername();

        for (int i = 0; i < topUsers.size(); i++) {
            User user = topUsers.get(i);
            boolean isTop3 = i < 3;
            boolean isCurrent = user.getUsername().equals(currentUsername);

            Label rankLabel = new Label(String.valueOf(i + 1), skin);
            Label userLabel = new Label(user.getUsername(), skin);
            Label scoreLabel = new Label(String.valueOf(user.getScore()), skin);
            Label killLabel = new Label(String.valueOf(user.getElimination()), skin);
            Label timeLabel = new Label(String.format("%.1f s", user.getSurvivalTime()), skin);

            if (isTop3) {
                rankLabel.setColor(Color.GOLD);
                userLabel.setColor(Color.GOLD);
                scoreLabel.setColor(Color.GOLD);
                killLabel.setColor(Color.GOLD);
                timeLabel.setColor(Color.GOLD);
            }

            if (isCurrent) {
                userLabel.setColor(Color.GREEN);
                scoreLabel.setColor(Color.GREEN);
                killLabel.setColor(Color.GREEN);
                timeLabel.setColor(Color.GREEN);
            }

            if (i % 2 == 0) {
                rankLabel.setColor(rankLabel.getColor().cpy().lerp(Color.LIGHT_GRAY, 0.5f));
                userLabel.setColor(userLabel.getColor().cpy().lerp(Color.LIGHT_GRAY, 0.5f));
                scoreLabel.setColor(scoreLabel.getColor().cpy().lerp(Color.LIGHT_GRAY, 0.5f));
                killLabel.setColor(killLabel.getColor().cpy().lerp(Color.LIGHT_GRAY, 0.5f));
                timeLabel.setColor(timeLabel.getColor().cpy().lerp(Color.LIGHT_GRAY, 0.5f));
            }

            table.add(rankLabel).left();
            table.add(userLabel).left();
            table.add(scoreLabel).left();
            table.add(killLabel).left();
            table.add(timeLabel).left();
            table.row();
        }

        ScrollPane scrollPane = new ScrollPane(table, skin);
        mainTable.add(scrollPane).colspan(5).width(Gdx.graphics.getWidth() * 0.8f).height(Gdx.graphics.getHeight() * 0.6f);
        mainTable.row().padTop(20);

        TextButton backButton = new TextButton("⬅ Back", skin);
        backButton.getLabel().setFontScale(1.2f);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        mainTable.add(backButton).colspan(5).padTop(10);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
