package com.tilldawn.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.model.SaveUserToJson;

import java.awt.*;


public class PauseMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private Table root;

    private final GameView gameScreen;

    public PauseMenuView(Skin skin, GameView gameScreen) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.gameScreen = gameScreen;

        Gdx.input.setInputProcessor(stage);
        createUI();
    }

    private void createUI() {
        Texture bgTexture = new Texture("backgrounds/36.png");
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);
        root = new Table(skin);
        root.setFillParent(true);
        root.center();
        stage.addActor(root);


        TextButton resumeBtn = new TextButton("Resume", skin);
        TextButton cheatsBtn = new TextButton("Cheat Codes", skin);
        TextButton abilitiesBtn = new TextButton("Show Abilities", skin);
        TextButton giveUpBtn = new TextButton("Give Up", skin);
        TextButton bwToggleBtn = new TextButton("Toggle B/W", skin);
        TextButton settingsBtn = new TextButton("Settings", skin);
        TextButton saveExitBtn = new TextButton("Save & Exit", skin);


        root.add(resumeBtn).pad(10).row();
        root.add(cheatsBtn).pad(10).row();
        root.add(abilitiesBtn).pad(10).row();
        root.add(giveUpBtn).pad(10).row();
        root.add(bwToggleBtn).pad(10).row();
        root.add(settingsBtn).pad(10).row();
        root.add(saveExitBtn).pad(10).row();


        resumeBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(gameScreen);
            }
        });

        cheatsBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                Main.getMain().setScreen(new CheatCodeScreen(gameScreen));
            }
        });

        abilitiesBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
//                String abilities = Main.getApp().getCurrentUser().getAbilities().toString();
//                showDialog("Abilities:\n" + abilities);
            }
        });

        giveUpBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SaveUserToJson.saveUserToJson(Main.getApp().getCurrentUser());

                Main.getMain().setScreen(new EndGameScreen(Main.getApp().getCurrentUser(), true));
            }
        });

        bwToggleBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

//                gameScreen.toggleBlackAndWhite();
            }
        });

        settingsBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new SettingMenuView(skin, PauseMenuView.this));
            }
        });

        saveExitBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
//                gameScreen.saveGame();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });
    }

    private void showDialog(String message) {
        Dialog dialog = new Dialog("Info", skin);
        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);
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
        Gdx.input.setInputProcessor(stage);
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
