package com.tilldawn.control;

import com.tilldawn.Main;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.MainMenuView;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view !=null){
            if (view.getPlayButton().isChecked()&&view.getField().getText().equals("kiarash")){
                Main.getMain().getScreen().dispose();
//                Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuView, GameAssetManager.().getSkin()));
            }
        }
    }
}
