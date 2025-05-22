package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.control.SignUpController;
import com.tilldawn.model.User;

import static com.tilldawn.Main.getApp;
import static com.tilldawn.Main.getBatch;

public class MainMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private User currentUser;

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.currentUser = getApp().getCurrentUser();

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);


        Image avatar = new Image(new Texture(Gdx.files.internal(currentUser.getAvatarPath())));
        Label username = new Label("User: " + currentUser.getUsername(), skin);
        Label score = new Label("Score: " + currentUser.getScore(), skin);


        TextButton continueBtn = new TextButton("Continue", skin);
        TextButton settingsBtn = new TextButton("Settings", skin);
        TextButton profileBtn = new TextButton("Profile", skin);
        TextButton preGameBtn = new TextButton("Pre-game", skin);
        TextButton leaderboardBtn = new TextButton("Leaderboard", skin);
        TextButton talentBtn = new TextButton("Hints / Talents", skin);
        TextButton logoutBtn = new TextButton("Log Out", skin);


        root.add(avatar).colspan(2).pad(10).row();
        root.add(username).pad(5).row();
        root.add(score).pad(5).row();

        root.add(continueBtn).width(200).pad(10).row();
        root.add(preGameBtn).width(200).pad(10).row();
        root.add(leaderboardBtn).width(200).pad(10).row();
        root.add(profileBtn).width(200).pad(10).row();
        root.add(settingsBtn).width(200).pad(10).row();
        root.add(talentBtn).width(200).pad(10).row();
        root.add(logoutBtn).width(200).pad(20).row();


//        continueBtn.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                if (controller.hasSavedGame()) {
//                    controller.loadSavedGame();
//                } else {
//                    continueBtn.setDisabled(true);
//                }
//            }
//        });

        logoutBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.logout();
                Main.getMain().setScreen(new SignUpMenuView(new SignUpController(), skin));
            }
        });


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

