package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Enum.Hero;
import com.tilldawn.Main;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.model.GameAssetManager;

import java.util.Map;

public class HintMenuView implements Screen {

    private Stage stage;
    private Skin skin;

    public HintMenuView(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Texture bgTexture=new Texture("backgrounds/12.png");
        Image background =new Image(bgTexture);
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
        for (Hero hero : Hero.values()) {

            root.add(new Label(hero.name() + "  HP :" + hero.getHP() + "  Speed :" + hero.getSpeed(), skin)).left().pad(5).row();
        }

        // Active controls
//        root.add(new Label("Active Controls:", skin)).padTop(20).left().row();
//        for (Map.Entry<String, String> entry : GameSettings.getActiveKeys().entrySet()) {
//            root.add(new Label(entry.getKey() + ": " + entry.getValue(), skin)).left().pad(3).row();
//        }

        // Cheat codes
//        root.add(new Label("Cheat Codes:", skin)).padTop(20).left().row();
//        for (String cheat : GameSettings.getEnabledCheats()) {
//            root.add(new Label("- " + cheat, skin)).left().row();
//        }

        // Abilities
//        root.add(new Label("Abilities:", skin)).padTop(20).left().row();
//        for (String ability : GameSettings.getAbilities()) {
//            root.add(new Label("- " + ability, skin)).left().row();
//        }

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
