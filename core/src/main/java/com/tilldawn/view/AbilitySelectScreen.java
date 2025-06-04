package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.control.AbilityController;
import com.tilldawn.model.User;
import com.tilldawn.Enum.Ability;

import java.security.SecureRandom;
import java.util.Collections;

public class AbilitySelectScreen implements Screen {

    private final Stage stage;
    private final Skin skin;
    private final GameView returnScreen;

    public AbilitySelectScreen(Skin skin, GameView returnScreen) {
        this.skin = skin;
        this.returnScreen = returnScreen;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        setupUI();
    }

    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label titleLabel = new Label("Choose One Ability", skin, "title");
        table.add(titleLabel).colspan(3).padBottom(20);
        table.row();
        SecureRandom random = new SecureRandom();
        int a = random.nextInt(1, 3);
        Array<Ability> randomAbilities = new Array<>();

        randomAbilities.add(Ability.getAbilityByIndex(a));
        randomAbilities.add(Ability.getAbilityByIndex(a + 1));
        randomAbilities.add(Ability.getAbilityByIndex(a + 2));


        for (final Ability ability : randomAbilities) {
            TextButton button = new TextButton(ability.name(), skin);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    User player = Main.getApp().getCurrentUser();
                    player.setAbility(ability);
                    AbilityController controller = new AbilityController();
                    controller.applyAbility(player, ability);
                    Main.getMain().setScreen(returnScreen);
                }
            });
            table.add(button).pad(10);
        }
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
