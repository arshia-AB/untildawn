package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
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
import com.tilldawn.control.ProfileController;
import com.tilldawn.control.SignUpController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.Result;
import com.tilldawn.model.User;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;


public class ProfileMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private TextField usernameField, passwordField;
    private Image avatarImage;
    private FileHandle selectedAvatar;

    public ProfileMenuView(Skin skin, ProfileController controller) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Table messageTable = new Table();
        messageTable.setFillParent(true);
        messageTable.left().pad(50);
        stage.addActor(messageTable);
        Label message = new Label("", skin);
        // Title
        table.add(new Label("Profile", skin, "title")).colspan(2).padBottom(30).row();

        // Username
        usernameField = new TextField("", skin);
        TextButton changeUsernameBtn = new TextButton("Change Username", skin);
        changeUsernameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = controller.ChangeUsername(usernameField.getText());
                message.setText(result.Message());
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Gdx.app.postRunnable(() -> {
                            message.setText("");
                        });
                    }
                }, 5);
            }

        });
        messageTable.add(message).width(100).height(50);

        table.add(new Label("Username", skin)).left();
        table.add(usernameField).width(300).row();
        table.add(changeUsernameBtn).colspan(2).padBottom(10).row();

        // Password
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        TextButton changePassBtn = new TextButton("Change Password", skin);
        changePassBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = controller.changePassword(passwordField.getText());
                message.setText(result.Message());
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Gdx.app.postRunnable(() -> {
                            message.setText("");
                        });
                    }
                }, 5);
            }
        });

        table.add(new Label("Password", skin)).left();
        table.add(passwordField).width(300).row();
        table.add(changePassBtn).colspan(2).padBottom(10).row();

        // Delete account
        TextButton deleteBtn = new TextButton("Delete Account", skin);

        deleteBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog confirmDialog = new Dialog("Confirm", skin) {
                    protected void result(Object object) {
                        if ((boolean) object) {
                            Result result = controller.DeleteAccount();
                            message.setText(result.Message());
                        }


                    }

                };

                confirmDialog.text("Are you sure you want to delete your account?");
                confirmDialog.button("Yes", true);
                confirmDialog.button("No", false);
                confirmDialog.show(stage);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Gdx.app.postRunnable(() -> {
                            message.setText("");
                        });
                    }
                }, 5);
            }
        });
        table.add(deleteBtn).colspan(2).padBottom(20).row();

        // Avatar section
        avatarImage = new Image(new Texture("avatar1.png"));
        TextButton changeAvatarBtn = new TextButton("Choose Avatar", skin);
//        changeAvatarBtn.addListener(...انتخاب از فایل یا لیست ...);

        table.add(avatarImage).colspan(2).padBottom(10).row();
        table.add(changeAvatarBtn).colspan(2).padBottom(10).row();

        // Back button
        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });
        table.add(backBtn).colspan(2);
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

    }

    // سایر متدهای Screen: resize, dispose ...
}
