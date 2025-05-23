package com.tilldawn.Enum;

public enum HeroEnum {
    SHANA("heros/T_Shana_Portrait.png", "SHANA", 4, 4),
    DIAMOND("heros/T_Diamond_Portrait.png", "DIAMOND", 7, 1),
    SCARLET("heros/T_Scarlett_Portrait.png", "SCARLET", 3, 5),
    LILITH("heros/T_Lilith_Portrait.png", "LILITH", 5, 3),
    DASHER("heros/T_Dasher_Portrait.png", "DASHER", 2, 10);
    private final String path;
    private final String Name;
    private final int HP;
    private final int Speed;

    HeroEnum(String path, String Name, int Hp, int Speed) {
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public HeroEnum getHeroByName(String name) {
        for (HeroEnum heroEnum : HeroEnum.values()) {
            if (heroEnum.getName().equals(name)) return heroEnum;
        }
        return null;
    }
}
