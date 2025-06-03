package com.tilldawn.Enum;

public enum Ability {
    VITALITY(1),
    DAMAGER(2),
    PROCREASE(3),
    AMOCREASE(4),
    SPEEDY(5);
    private final int index;

    Ability(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Ability getAbilityByIndex(int i) {
        for (Ability ability : Ability.values()) {
            if (ability.getIndex() == i) {
                return ability;
            }
        }
        return null;
    }


}
