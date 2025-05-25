package com.tilldawn.control;

import com.tilldawn.Enum.WeaponEnum;
import com.tilldawn.Main;
import com.tilldawn.model.User;
import com.tilldawn.model.Weapon;
import com.tilldawn.view.GameView;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;


    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController(Main.getApp().getCurrentUser());
        worldController = new WorldController(playerController);
        weaponController = new WeaponController(new Weapon(WeaponEnum.SMG));
    }

    public void updateGame() {
        if (view != null) {
            worldController.update();
            playerController.update();
            weaponController.update();
        }
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }
}
