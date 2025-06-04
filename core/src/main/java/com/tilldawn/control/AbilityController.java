package com.tilldawn.control;

import com.tilldawn.Enum.Ability;
import com.tilldawn.model.User;

import static com.tilldawn.Enum.Ability.*;

public class AbilityController {
    private float speedBoostTimeLeft = 0;
    private boolean isSpeedBoosted = false;

    private float damageBoostTimeLeft = 0;
    private boolean isDamageBoosted = false;
    private int originalDamage = -1;

    private final User player;

    public AbilityController(User player) {
        this.player = player;
    }

    public void applyAbility(Ability ability) {
        player.setAbility(ability);

        switch (ability) {
            case SPEEDY:
                if (!isSpeedBoosted) {
                    player.getHero().increaseSpeed(2);
                    isSpeedBoosted = true;
                }
                speedBoostTimeLeft = 10f;
                break;

            case DAMAGER:
                if (!isDamageBoosted) {
                    originalDamage = player.getWeapon().getWeaponEnum().getDamage();
                    int boosted = originalDamage * 5 / 4;
                    player.getWeapon().getWeaponEnum().setDamage(boosted);
                    isDamageBoosted = true;
                }
                damageBoostTimeLeft = 10f;
                break;

            case VITALITY:
                player.getHero().increaseHp(1);
                break;

            case AMOCREASE:
                player.getWeapon().getWeaponEnum().setMaxAmmo(
                    player.getWeapon().getWeaponEnum().getMaxAmmo() + 5
                );
                break;

            case PROCREASE:
                player.getWeapon().getWeaponEnum().setProjectTile(
                    player.getWeapon().getWeaponEnum().getProjectTile() + 1
                );
                break;
        }
    }

    public void update(float deltaTime) {
        if (isSpeedBoosted) {
            speedBoostTimeLeft -= deltaTime;
            if (speedBoostTimeLeft <= 0) {
                player.getHero().increaseSpeed(-2);
                isSpeedBoosted = false;
            }
        }

        if (isDamageBoosted) {
            damageBoostTimeLeft -= deltaTime;
            if (damageBoostTimeLeft <= 0) {
                player.getWeapon().getWeaponEnum().setDamage(originalDamage);
                isDamageBoosted = false;
            }
        }
    }
}
