package com.tilldawn.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.control.SignUpController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;
import com.badlogic.gdx.audio.Music;


public class SettingMenuView implements Screen {
    private Stage stage;
    private Skin skin;

    private ShaderProgram grayscaleShader;
    private Texture bgTexture;
    private SpriteBatch batch;

    public SettingMenuView(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("Settings", skin, "title");

        Label musicLabel = new Label("Music Volume", skin);
        Slider musicSlider = new Slider(0, 1, 0.01f, false, skin);
        musicSlider.setValue(0.5f);

        Label musicSelectLabel = new Label("Select Music", skin);
        SelectBox<String> musicSelect = new SelectBox<>(skin);
        musicSelect.setItems("Track 1", "Track 2", "Track 3", "No music");
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (App.currentMusic != null) {
                    float volume = musicSlider.getValue();
                    App.currentMusic.setVolume(volume);
                }

            }
        });
        musicSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = musicSelect.getSelected();
                switch (selected) {
                    case "Track 1":
                        playMusic("Track 1.mp3");
                        break;
                    case "Track 2":
                        playMusic("Track 2.mp3");
                        break;
                    case "Track 3":
                        playMusic("Track 3.mp3");
                        break;
                    case "No music":
                        if (App.currentMusic != null) {
                            App.currentMusic.stop();
                        }
                        break;


                }
            }
        });
        CheckBox sfxCheck = new CheckBox("Enable SFX", skin);
        sfxCheck.setChecked(true);

        TextButton changeKeyBtn = new TextButton("Change Controls", skin);


        CheckBox autoReloadCheck = new CheckBox("Enable Auto Reload", skin);


        CheckBox grayscaleCheck = new CheckBox("Grayscale Mode", skin);
        grayscaleCheck.setChecked(App.grayscaleEnabled);

        grayscaleCheck.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.grayscaleEnabled = grayscaleCheck.isChecked();
            }
        });

        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });


        table.add(title).colspan(2).padBottom(30).row();
        table.add(musicLabel).left();
        table.add(musicSlider).width(300).row();
        table.add(musicSelectLabel).left();
        table.add(musicSelect).width(300).row();
        table.add(sfxCheck).colspan(2).left().padTop(10).row();
        table.add(autoReloadCheck).colspan(2).left().padTop(10).row();
        table.add(grayscaleCheck).colspan(2).left().padTop(10).row();
        table.add(changeKeyBtn).colspan(2).padTop(10).row();
        table.add(backBtn).colspan(2).padTop(30);
    }

    private void playMusic(String fileName) {
        if (App.currentMusic != null) {
            App.currentMusic.setLooping(false);
            App.currentMusic.stop();
            App.currentMusic.dispose();
        }

        App.currentMusic = Gdx.audio.newMusic(Gdx.files.internal("music/" + fileName));
        App.currentMusic.setLooping(true);
        App.currentMusic.setVolume(0.5f);
        App.currentMusic.play();
    }


    @Override
    public void show() {
        ShaderProgram.pedantic = false;
        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shader/grayscale.vertex.glsl"),
            Gdx.files.internal("shader/grayscale.fragment.glsl")
        );
        if (!grayscaleShader.isCompiled()) {
            System.err.println(grayscaleShader.getLog());
        }

        bgTexture = new Texture(Gdx.files.internal("backgrounds/17.png"));
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.setProjectionMatrix(stage.getCamera().combined);

        if (App.grayscaleEnabled) {
            batch.setShader(grayscaleShader);
        } else {
            batch.setShader(null);
        }

        batch.begin();
        batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        batch.dispose();
        bgTexture.dispose();
        grayscaleShader.dispose();
    }
}
