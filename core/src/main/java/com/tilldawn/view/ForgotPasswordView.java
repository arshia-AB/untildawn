package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.control.LoginController;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.control.SignUpController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.Result;
import com.tilldawn.model.User;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static com.tilldawn.Main.getMain;

public class ForgotPasswordView implements Screen {
    private Stage stage;
    private Skin skin;

    public ForgotPasswordView(Skin skin, LoginController controller) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Texture bgTexture = new Texture(Gdx.files.internal("foeget.jpg"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("Recover Password", skin);
        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");

        TextField answerField = new TextField("", skin);
        answerField.setMessageText("Security Answer");

        TextField newPasswordField = new TextField("", skin);
        newPasswordField.setMessageText("New Password");

        Label messageLabel = new Label("", skin);
        TextButton confirmButton = new TextButton("Confirm", skin);
        TextButton backButton = new TextButton("Back", skin);

        confirmButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String user = usernameField.getText();
                String answer = answerField.getText();
                String newPass = newPasswordField.getText();

                Result result = controller.resetPassword(user, answer, newPass);
                messageLabel.setText(result.Message());
                if (result.IsSuccess()) {
                    getMain().setScreen(new SignUpMenuView(new SignUpController(), GameAssetManager.getGameAssetManager().getSkin()));

                }
            }
        });

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                getMain().setScreen(new SignUpMenuView(new SignUpController(), GameAssetManager.getGameAssetManager().getSkin()));

            }
        });

        table.add(title).padBottom(20).row();
        table.add(usernameField).pad(10).width(300).row();
        table.add(answerField).pad(10).width(300).row();
        table.add(newPasswordField).pad(10).width(300).row();
        table.add(confirmButton).padTop(20).row();
        table.add(backButton).padTop(10).row();
        table.add(messageLabel).padTop(20);
    }

    @Override
    public void show() {
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
        stage.dispose();
    }
}

