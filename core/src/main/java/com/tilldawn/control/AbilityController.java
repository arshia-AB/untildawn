package com.tilldawn.control;

import com.tilldawn.Enum.Ability;
import com.tilldawn.model.User;

import static com.tilldawn.Enum.Ability.*;

public class AbilityController {

    public void applyAbility(User player, Ability ability) {
        player.setAbility(ability);

        switch (ability) {
            case AMOCREASE:
                player.getWeapon().getWeaponEnum().setMaxAmmo(
                    player.getWeapon().getWeaponEnum().getMaxAmmo() + 5
                );
                break;

            case DAMAGER:
                player.getWeapon().getWeaponEnum().setDamage(
                    player.getWeapon().getWeaponEnum().getDamage() * 5 / 4
                );
                break;

            case VITALITY:
                player.getHero().increaseHp(1);
                break;

            case SPEEDY:
                player.getHero().increaseSpeed(2);
                break;

            case PROCREASE:
                player.getWeapon().getWeaponEnum().setProjectTile(
                    player.getWeapon().getWeaponEnum().getProjectTile() + 1
                );
                break;
        }
    }
}
