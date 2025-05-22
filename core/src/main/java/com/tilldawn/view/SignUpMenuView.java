package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.control.SignUpController;
import com.tilldawn.model.User;

public class SignUpMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private SignUpController controller = new SignUpController();

    public SignUpMenuView(Skin skin) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("username");

        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("password");

        TextField securityField = new TextField("", skin);
        securityField.setMessageText("your first pet name ?");

        Label message = new Label("", skin);

        TextButton registerBtn = new TextButton("SignUp", skin);
        registerBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String result = controller.register(
                    usernameField.getText(),
                    passwordField.getText(),
                    securityField.getText()
                );
                message.setText(result);
            }
        });

        TextButton guestBtn = new TextButton("play as a guest", skin);
        guestBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                User guest = controller.loginAsGuest();
                message.setText("login as a guest" + guest.getUsername());
            }
        });

        table.add(usernameField).width(300).pad(10);
        table.row();
        table.add(passwordField).width(300).pad(10);
        table.row();
        table.add(securityField).width(300).pad(10);
        table.row();
        table.add(registerBtn).pad(10);
        table.row();
        table.add(guestBtn).pad(10);
        table.row();
        table.add(message).pad(10);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
