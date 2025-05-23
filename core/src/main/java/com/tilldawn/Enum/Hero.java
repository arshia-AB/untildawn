package com.tilldawn.Enum;

public enum Hero {
    SHANA("SHANA", 4, 4),
    DIAMOND("DIAMOND", 7, 1),
    SCARLET("SCARLET", 3, 5),
    LILITH("LILITH", 5, 3),
    DASHER("DASHER", 2, 10);
    private String Name;
    private int HP;
    private int Speed;

    Hero(String Name, int Hp, int Speed) {
        this.Name = Name;
        this.HP = Hp;
        this.Speed = Speed;
    }

    public String getName() {
        return Name;
    }

    public int getHP() {
        return HP;
    }

    public int getSpeed() {
        return Speed;
    }

    public Hero getHeroByName(String name) {
        for (Hero hero : Hero.values()) {
            if (hero.getName().equals(name)) return hero;
        }
        return null;
    }
}
