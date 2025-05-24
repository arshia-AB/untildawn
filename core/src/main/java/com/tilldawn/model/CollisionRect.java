package com.tilldawn.model;

public class CollisionRect {
    float x, y;
    float width, height;

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public CollisionRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean collideswith(CollisionRect rect) {
        return x < rect.x + rect.width && x + width > rect.x && y + height > rect.y && y < rect.y + rect.height;
    }
}
