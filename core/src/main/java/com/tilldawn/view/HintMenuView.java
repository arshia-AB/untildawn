package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Enum.HeroEnum;
import com.tilldawn.Main;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.model.GameAssetManager;

public class HintMenuView implements Screen {

    private Stage stage;
    private Skin skin;

    public HintMenuView(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Texture bgTexture = new Texture("backgrounds/12.png");
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);
        Table root = new Table();
        root.setFillParent(true);
        root.top().pad(20);
        stage.addActor(root);

        // Title
        Label title = new Label("Hint Menu", skin, "title");
        root.add(title).colspan(2).center().padBottom(20).row();

        // Hero hints
        root.add(new Label("Hero Hints:", skin)).left().row();
        for (HeroEnum heroEnum : HeroEnum.values()) {

            root.add(new Label(heroEnum.name() + "  HP :" + heroEnum.getHP() + "  Speed :" + heroEnum.getSpeed(), skin)).left().pad(5).row();
        }
        root.add(new Label("Cheat Codes:", skin)).padTop(20).left().row();
        root.add(new Label("- ⏳ decrease game time: reduce game time by 60 seconds", skin)).left().pad(2).row();
        root.add(new Label("- 🆙 level up: instantly level up", skin)).left().pad(2).row();
        root.add(new Label("- ❤️ increase HP: restore full health", skin)).left().pad(2).row();
        root.add(new Label("- 💀 go to boss fight: skip to boss level", skin)).left().pad(2).row();
        root.add(new Label("- ⭐ get score: gain 500 score instantly", skin)).left().pad(2).row();

        root.add(new Label("Active Controls:", skin)).padTop(20).left().row();
        root.add(new Label("- W: Move Up", skin)).left().pad(2).row();
        root.add(new Label("- S: Move Down", skin)).left().pad(2).row();
        root.add(new Label("- A: Move Left", skin)).left().pad(2).row();
        root.add(new Label("- D: Move Right", skin)).left().pad(2).row();
        root.add(new Label("- R: Reload Weapon", skin)).left().pad(2).row();


        // Back button
        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(event -> {
            if (backBtn.isPressed()) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin())); // or switch to previous screen
            }
            return true;
        });
        root.add(backBtn).colspan(2).center().padTop(30).row();
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
