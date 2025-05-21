package com.tilldawn.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.tilldawn.control.MainMenuController;

public class MainMenuView implements Screen {
    private Stage stage;
    private final TextButton playButton;
    private final Label gameTitle;
    private final TextField field;
    public Table table;
    private final MainMenuController controller;

    public MainMenuView(Skin skin, MainMenuController controller) {
        this.controller = controller;
        this.playButton = new TextButton("button", skin);
        this.gameTitle = new Label("this is a title", skin);
        this.field = new TextField("this is a field", skin);
        this.table = new Table();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

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
}
