package com.mygdx.game;

public class Vector2D {
    private float componentX;
    private float componentY;

    public Vector2D(float componentX, float componentY) {
        this.componentX = componentX;
        this.componentY = componentY;
    }

    public float getComponentX() { return this.componentX; }
    public float getComponentY() { return this.componentY; }

    public void setComponentX(float componentX) { this.componentX = componentX; }
    public void setComponentY(float componentY) { this.componentY = componentY; }

	public void changeComponents(float componentX, float componentY) {
    	this.componentX += componentX;
    	this.componentY += componentY;
    }
}
