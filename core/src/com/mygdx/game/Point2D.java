package com.mygdx.game;

public class Point2D {
    private float positionX;
    private float positionY;

    public Point2D(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public float getPositionX() { return this.positionX; }
    public float getPositionY() { return this.positionY; }
    public void setPositionX(float positionX) { this.positionX = positionX; }
    public void setPositionY(float positionY) { this.positionY = positionY; }

    public void move(Vector2D vector) {
        this.positionX += vector.getComponentX();
        this.positionY += vector.getComponentY();
    }

    @Override
    public String toString() {
        return "(" + positionX + ", " + positionY + ")";
    }
}
