package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;

import java.util.*;
import java.util.List;

public class ScoreboardScreen extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Table scoreTable;
    private ScrollPane scrollPane;
    private SelectBox<String> sortBox;

    private List<User> users;

    public ScoreboardScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        Texture bgTexture = new Texture("backgrounds/25.png");
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);
        users = new ArrayList<>(Main.getApp().getAllUsers().values());

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top().padTop(30);
        stage.addActor(mainTable);

        Label title = new Label("🏆 Top 10 Players", skin);
        title.setFontScale(2f);
        title.setAlignment(Align.center);
        mainTable.add(title).colspan(2).padBottom(5);
        mainTable.row();

        sortBox = new SelectBox<>(skin);
        sortBox.setItems("Score", "Username", "Kills", "Survival Time");
        sortBox.getSelection().set("Score");

        sortBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateScoreTable();
            }
        });

        mainTable.add(new Label("Sort by: ", skin)).padRight(25).left();
        mainTable.add(sortBox).padBottom(5).left();
        mainTable.row();


        scoreTable = new Table();
        scrollPane = new ScrollPane(scoreTable, skin);
        scrollPane.setScrollingDisabled(true, false);
        mainTable.add(scrollPane).colspan(2).width(Gdx.graphics.getWidth() * 0.9f).height(Gdx.graphics.getHeight() * 0.6f);
        mainTable.row().padTop(15);

        TextButton backButton = new TextButton("⬅ Back", skin);
        backButton.getLabel().setFontScale(1.2f);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        mainTable.add(backButton).colspan(2);

        updateScoreTable();
    }

    private void updateScoreTable() {
        scoreTable.clear();
        String sortBy = sortBox.getSelected();

        Comparator<User> comparator;
        switch (sortBy) {
            case "Username":
                comparator = Comparator.comparing(User::getUsername);
                break;
            case "Kills":
                comparator = Comparator.comparingInt(User::getElimination).reversed();
                break;
            case "Survival Time":
                comparator = Comparator.comparingDouble(User::getSurvivalTime).reversed();
                break;
            case "Score":
            default:
                comparator = Comparator.comparingInt(User::getScore).reversed();
                break;
        }

        users.sort(comparator);
        List<User> topUsers = users.subList(0, Math.min(10, users.size()));
        String currentUsername = Main.getApp().getCurrentUser().getUsername();

        scoreTable.add(new Label("Rank", skin)).left().pad(50);
        scoreTable.add(new Label("Username", skin)).left().pad(50);
        scoreTable.add(new Label("Score", skin)).left().pad(50);
        scoreTable.add(new Label("Kills", skin)).left().pad(50);
        scoreTable.add(new Label("Time", skin)).left().pad(50);
        scoreTable.row();

        for (int i = 0; i < topUsers.size(); i++) {
            User user = topUsers.get(i);
            boolean isTop3 = i < 3;
            boolean isCurrent = user.getUsername().equals(currentUsername);

            Label rank = new Label(String.valueOf(i + 1), skin);
            Label name = new Label(user.getUsername(), skin);
            Label score = new Label(String.valueOf(user.getScore()), skin);
            Label kills = new Label(String.valueOf(user.getElimination()), skin);
            Label time = new Label(String.format("%.1f s", user.getSurvivalTime()), skin);

            if (isTop3) {
                rank.setColor(Color.GOLD);
                name.setColor(Color.GOLD);
                score.setColor(Color.GOLD);
                kills.setColor(Color.GOLD);
                time.setColor(Color.GOLD);
            }

            if (isCurrent) {
                name.setColor(Color.GREEN);
                score.setColor(Color.GREEN);
                kills.setColor(Color.GREEN);
                time.setColor(Color.GREEN);
            }

            scoreTable.add(rank).left().pad(50);
            scoreTable.add(name).left().pad(50);
            scoreTable.add(score).left().pad(50);
            scoreTable.add(kills).left().pad(50);
            scoreTable.add(time).left().pad(50);
            scoreTable.row();
        }
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
