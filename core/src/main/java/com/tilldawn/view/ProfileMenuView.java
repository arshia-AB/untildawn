package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Enum.Avatar;
import com.tilldawn.Main;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.control.ProfileController;
import com.tilldawn.model.Result;

public class ProfileMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private TextField usernameField, passwordField;
    private FileHandle selectedAvatar;

    public ProfileMenuView(Skin skin, ProfileController controller) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Background
        Texture bgTexture = new Texture(Gdx.files.internal("backgrounds/1.png"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        // Main Table
        Table table = new Table();
        table.setFillParent(true);
        table.pad(30);
        stage.addActor(table);

        // Message Table
        Table messageTable = new Table();
        messageTable.setFillParent(true);
        messageTable.left().pad(50);
        stage.addActor(messageTable);
        Label message = new Label("", skin);
        messageTable.add(message).width(200).height(50);

        // Title
        table.add(new Label("Profile", skin, "title")).colspan(2).padBottom(30).row();

        // Username
        usernameField = new TextField("", skin);
        usernameField.setMessageText("enter new username");
        TextButton changeUsernameBtn = new TextButton("Change Username", skin);
        changeUsernameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = controller.ChangeUsername(usernameField.getText());
                message.setText(result.Message());
                scheduleMessageClear(message);
            }
        });
        table.add(usernameField).padLeft(200).width(500).row();
        table.add(changeUsernameBtn).padLeft(200).padBottom(10).row();

        // Password
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        passwordField.setMessageText("enter new password");

        TextButton changePassBtn = new TextButton("Change Password", skin);
        changePassBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = controller.changePassword(passwordField.getText());
                message.setText(result.Message());
                scheduleMessageClear(message);
            }
        });
        table.add(passwordField).padLeft(200).width(500).row();
        table.add(changePassBtn).padLeft(200).padBottom(10).row();

        // Delete Account
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
                scheduleMessageClear(message);
            }
        });
        table.add(deleteBtn).padLeft(200).padBottom(20).row();

        // Avatar Preview
        Image previewImage = new Image();
        table.add(previewImage).left().padBottom(10).row();

        // Avatar Selection
        Table avatarTable = new Table();
        for (Avatar avatar : Avatar.values()) {
            Texture texture = new Texture(Gdx.files.internal(avatar.getPath()));
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));

            ImageButton avatarBtn = new ImageButton(style);
            avatarBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedAvatar = Gdx.files.internal(avatar.getPath());
                    previewImage.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
                    Main.getApp().getCurrentUser().setAvatarPath(avatar.getPath());
                }
            });
            avatarTable.add(avatarBtn).size(64).pad(10);
        }

        ScrollPane scrollPane = new ScrollPane(avatarTable, skin);
        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setFadeScrollBars(false);
        table.add(scrollPane).colspan(2).height(100).width(Gdx.graphics.getWidth() * 0.8f).padBottom(20).row();

        // Back Button
        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });
        table.add(backBtn).colspan(2);
    }

    private void scheduleMessageClear(Label message) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> message.setText(""));
            }
        }, 5);
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
}
