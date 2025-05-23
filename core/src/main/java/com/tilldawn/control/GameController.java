package com.tilldawn.control;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tilldawn.view.GameView;

import javax.swing.text.View;

public class GameController {
    private GameView view;

    public void setView(GameView view) {

        this.view = view;
    }

}
