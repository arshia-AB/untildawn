package com.tilldawn.control;

import com.badlogic.gdx.Screen;
import com.tilldawn.model.PreGame;
import com.tilldawn.view.PreGameMenuView;

public class PreGameMenuController {
    private PreGame preGame;

    private PreGameMenuView view;

    public void setView(PreGameMenuView view) {
        this.view = view;
        this.preGame = new PreGame();
    }

    public void handlePreGameMenuButtons() {
        if (view != null) {
        }
    }
}
