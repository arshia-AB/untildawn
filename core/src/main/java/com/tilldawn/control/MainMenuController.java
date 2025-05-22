package com.tilldawn.control;

import com.badlogic.gdx.Gdx;
import com.tilldawn.Main;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.Result;
import com.tilldawn.view.MainMenuView;
import com.tilldawn.view.PreGameMenuView;
import com.tilldawn.view.SignUpMenuView;

import static com.tilldawn.Main.getApp;
import static com.tilldawn.Main.getMain;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void logout() {
        getApp().setCurrentUser(null);
        getMain().setScreen(new SignUpMenuView(new SignUpController(), GameAssetManager.getGameAssetManager().getSkin()));

    }
}
