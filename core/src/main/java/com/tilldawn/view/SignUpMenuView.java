package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.control.SignUpController;
import com.tilldawn.model.User;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SignUpMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private final float FIELD_WIDTH_RATIO = 0.8f;

    public SignUpMenuView(SignUpController controller, Skin skin) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Texture bgTexture = new Texture(Gdx.files.internal("20-minutes-till-dawn-huwun.png"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        Table titleTable = new Table();
        titleTable.setFillParent(true);
        titleTable.top().top();
        stage.addActor(titleTable);

        Label title = new Label("Create Your Account", skin, "title");
        titleTable.add(title).center();

        Table formTable = new Table();
        formTable.setFillParent(true);
        formTable.bottom().left().padBottom(30).padLeft(30);
        stage.addActor(formTable);
        Table ButtonTable = new Table();
        ButtonTable.setFillParent(true);
        ButtonTable.bottom().right().padBottom(30).padRight(30);
        stage.addActor(ButtonTable);

        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");

        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Password");

        TextField securityField = new TextField("", skin);
        securityField.setMessageText("Your first pet's name?");

        Label message = new Label("", skin);

        TextButton registerBtn = new TextButton("Sign Up", skin);
        TextButton guestBtn = new TextButton("Play as Guest", skin);

        registerBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String result = controller.register(
                    usernameField.getText(),
                    passwordField.getText(),
                    securityField.getText()
                );
                message.setText(result);
            }
        });

        guestBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User guest = controller.loginAsGuest();
                message.setColor(Color.GREEN);
                message.setText("Logged in as guest : " + guest.getUsername());
            }
        });

        float fieldWidth = Gdx.graphics.getWidth() * FIELD_WIDTH_RATIO;

        formTable.add(usernameField).width(fieldWidth).pad(10);
        formTable.row();
        formTable.add(passwordField).width(fieldWidth).pad(10);
        formTable.row();
        formTable.add(securityField).width(fieldWidth).pad(10);
        formTable.row();

        ButtonTable.add(registerBtn).width(fieldWidth / 2).padTop(20);
        ButtonTable.row();

        ButtonTable.add(guestBtn).width(fieldWidth).padTop(10);
        ButtonTable.row();

        ButtonTable.add(message).padTop(20);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
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
