package com.tilldawn.control;

import com.badlogic.gdx.Gdx;
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
    private EnemyController enemyController;


    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController(Main.getApp().getCurrentUser());
        worldController = new WorldController(playerController);
        weaponController = new WeaponController(new Weapon(WeaponEnum.SMG));
        enemyController = new EnemyController();
    }

    public void updateGame() {
        if (view != null) {
            worldController.update();
            playerController.update();
            weaponController.update();
            enemyController.update(Gdx.graphics.getDeltaTime(), playerController.getPlayer().getPosition(), Main.getBatch());
        }
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }
}
