package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.control.PreGameMenuController;
import com.tilldawn.control.ProfileController;
import com.tilldawn.control.SignUpController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;

import static com.tilldawn.Main.getApp;
import static com.tilldawn.Main.getBatch;

public class MainMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private User currentUser;
    private final float FIELD_WIDTH_RATIO = 0.8f;

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.currentUser = getApp().getCurrentUser();
        Texture bgtexture = new Texture(Gdx.files.internal("backgrounds/2.png"));
        Image background = new Image(bgtexture);
        background.setFillParent(true);
        stage.addActor(background);
        Table root = new Table();
        root.setFillParent(true);
        root.left();
        stage.addActor(root);
//        Table info = new Table(skin);
//        info.setFillParent(true);
//
//        stage.addActor(info);


        Image avatar = new Image(new Texture(Gdx.files.internal(currentUser.getAvatarPath())));

        Label username = new Label("User: " + currentUser.getUsername(), skin);
        username.setColor(Color.GOLDENROD);
        username.setFontScale(1.5f);
        Label score = new Label("Score: " + currentUser.getScore(), skin);
        score.setColor(Color.GOLD);
        score.setFontScale(1.5f);


        TextButton continueBtn = new TextButton("Continue", skin);
        TextButton settingsBtn = new TextButton("Settings", skin);
        TextButton profileBtn = new TextButton("Profile", skin);
        TextButton preGameBtn = new TextButton("Pre-game", skin);
        TextButton leaderboardBtn = new TextButton("Leaderboard", skin);
        TextButton talentBtn = new TextButton("Hints / Talents", skin);
        TextButton logoutBtn = new TextButton("Log Out", skin);

        float fieldWidth = Gdx.graphics.getWidth() * FIELD_WIDTH_RATIO;
        Table userInfoTable = new Table(skin);
        userInfoTable.right();
        userInfoTable.pad(10);
        userInfoTable.add(avatar).size(320).padBottom(10).row();
        userInfoTable.add(username).pad(50).row();
        userInfoTable.add(score).pad(50).row();

        stage.addActor(userInfoTable);

        userInfoTable.setFillParent(true);

        root.add(continueBtn).width(fieldWidth / 2).right().pad(10).row();
        root.add(preGameBtn).width(fieldWidth / 2).pad(10).row();
        root.add(leaderboardBtn).width(fieldWidth / 2).pad(10).row();
        root.add(profileBtn).width(fieldWidth / 2).pad(10).row();
        root.add(settingsBtn).width(fieldWidth / 2).pad(10).row();
        root.add(talentBtn).width(fieldWidth / 2).pad(10).row();
        root.add(logoutBtn).width(fieldWidth / 2).pad(20).row();


//        continueBtn.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                if (controller.hasSavedGame()) {
//                    controller.loadSavedGame();
//                } else {
//                    continueBtn.setDisabled(true);
//                }
//            }
//        });
        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new SettingMenuView(GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
        preGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
        profileBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new ProfileMenuView(GameAssetManager.getGameAssetManager().getSkin(), new ProfileController()));

            }
        });


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

