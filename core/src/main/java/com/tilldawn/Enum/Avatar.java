package com.tilldawn.Enum;

public enum Avatar {

    //shayan soleymani
    AVATAR_1(1, "avatars/vibrent_1.png"),

    AVATAR_2(2, "avatars/vibrent_2.png"),

    AVATAR_3(3, "avatars/vibrent_3.png"),

    AVATAR_4(4, "avatars/vibrent_4.png"),

    AVATAR_5(5, "avatars/vibrent_5.png"),

    AVATAR_6(6, "avatars/vibrent_6.png"),

    AVATAR_7(7, "avatars/vibrent_7.png"),

    AVATAR_8(8, "avatars/vibrent_8.png"),

    AVATAR_9(9, "avatars/vibrent_9.png"),

    AVATAR_10(10, "avatars/vibrent_10.png"),

    AVATAR_11(11, "avatars/vibrent_11.png"),

    AVATAR_12(12, "avatars/vibrent_12.png"),

    AVATAR_13(13, "avatars/vibrent_13.png"),

    AVATAR_14(14, "avatars/vibrent_14.png"),

    AVATAR_15(15, "avatars/vibrent_15.png"),

    AVATAR_16(16, "avatars/vibrent_16.png"),

    AVATAR_17(17, "avatars/vibrent_17.png"),

    AVATAR_18(18, "avatars/vibrent_18.png"),

    AVATAR_19(19, "avatars/vibrent_19.png"),

    AVATAR_20(20, "avatars/vibrent_20.png"),

    AVATAR_21(21, "avatars/vibrent_21.png"),

    AVATAR_22(22, "avatars/vibrent_22.png"),

    AVATAR_23(23, "avatars/vibrent_23.png"),

    AVATAR_24(24, "avatars/vibrent_24.png"),

    AVATAR_25(25, "avatars/vibrent_25.png"),

    AVATAR_26(26, "avatars/vibrent_26.png"),

    AVATAR_27(27, "avatars/vibrent_27.png");

    private final int id;
    private final String path;

    Avatar(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public static Avatar fromId(int id) {
        for (Avatar avatar : values()) {
            if (avatar.id == id) return avatar;
        }
        return null;
    }


}
