package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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

public class SignUpMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private final float FIELD_WIDTH_RATIO = 0.8f;
    private final LoginController controller2 = new LoginController();


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
        ButtonTable.bottom().padBottom(10);
        stage.addActor(ButtonTable);
        Table exitTable = new Table();
        exitTable.setFillParent(true);
        exitTable.top().right().pad(100);
        stage.addActor(exitTable);

        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");

        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Password");

        TextField securityField = new TextField("", skin);
        securityField.setMessageText("Your first pet's name?");

        Label message = new Label("", skin);
        TextButton exit = new TextButton("EXIT", skin);
        TextButton forgetBtn = new TextButton("forget password", skin);
        TextButton loginBtn = new TextButton("Login", skin);
        TextButton registerBtn = new TextButton("Sign Up", skin);
        TextButton guestBtn = new TextButton("Play as Guest", skin);
        exit.getLabel().setColor(Color.RED);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        loginBtn.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {

                message.setText("");
                if (!securityField.isVisible() && usernameField.isVisible()) {
                    if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                        message.setColor(Color.RED);
                        message.setText("Please fill all fields.");

                    } else {
                        Result result = controller2.Login(usernameField.getText(), passwordField.getText());
                        message.setText(result.Message());
                        if (result.IsSuccess()) {
                            Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));

                        } else {
                            forgetBtn.setVisible(true);
                            forgetBtn.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {

                                    Main.getMain().setScreen(new ForgotPasswordView(skin, controller2));
                                }
                            });
                        }


                    }
                } else {

                    forgetBtn.setVisible(false);
                    usernameField.setVisible(true);
                    passwordField.setVisible(true);
                    securityField.setVisible(false);
                }


            }
        });
        registerBtn.addListener(new ClickListener() {


            @Override
            public void clicked(InputEvent event, float x, float y) {
                message.setText("");

                if (securityField.isVisible()) {
                    if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || securityField.getText().isEmpty()) {
                        message.setColor(Color.RED);
                        message.setText("Please fill all fields.");
                        return;
                    }
                    Result result = controller.register(usernameField.getText(), passwordField.getText(), securityField.getText());

                    message.setText(result.Message());
                    if (result.IsSuccess()) {
                        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                    }
                } else {

                    forgetBtn.setVisible(false);
                    usernameField.setVisible(true);
                    passwordField.setVisible(true);
                    securityField.setVisible(true);
                }

            }
        });

        guestBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                message.setText("");

                forgetBtn.setVisible(false);
                usernameField.setVisible(false);
                passwordField.setVisible(false);
                securityField.setVisible(false);
                User guest = controller.loginAsGuest();
                Main.getApp().setCurrentUser(guest);
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));

            }
        });


        float fieldWidth = Gdx.graphics.getWidth() * FIELD_WIDTH_RATIO;
        exitTable.add(exit).width(fieldWidth / 10);
        formTable.add(forgetBtn).width(fieldWidth / 3).pad(10);
        formTable.row();
        formTable.add(usernameField).width(fieldWidth / 4).pad(10);
        formTable.row();
        formTable.add(passwordField).width(fieldWidth / 4).pad(10);
        formTable.row();
        formTable.add(securityField).width(fieldWidth / 4).pad(10);
        formTable.row();
        forgetBtn.setVisible(false);
        usernameField.setVisible(false);
        passwordField.setVisible(false);
        securityField.setVisible(false);
        ButtonTable.add(loginBtn).width(fieldWidth / 4).padTop(20);
        ButtonTable.row();
        ButtonTable.add(registerBtn).width(fieldWidth / 4).padTop(10);
        ButtonTable.row();

        ButtonTable.add(guestBtn).width(fieldWidth / 4).padTop(10);
        ButtonTable.row();

        ButtonTable.add(message).padTop(20);

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
