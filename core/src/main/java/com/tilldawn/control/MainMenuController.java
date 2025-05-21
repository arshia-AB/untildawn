package com.tilldawn.control;

import com.badlogic.gdx.Gdx;
import com.tilldawn.Main;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.MainMenuView;
import com.tilldawn.view.PreGameMenuView;

import static com.tilldawn.Main.getMain;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view != null) {
            if (view.getPlayButton().isChecked() && view.getField().getText().equals("kiarash")) {
                getMain().getScreen().dispose();
                getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                
            }
        }
    }
}
