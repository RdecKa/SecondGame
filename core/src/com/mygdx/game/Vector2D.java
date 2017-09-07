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

    public Vector2D getPerpendicularVector() {
    	return new Vector2D(componentY, -componentX);
	}

	public static float dotProduct(Vector2D v1, Vector2D v2) {
    	return (v1.componentX * v2.componentX + v1.componentY * v2.componentY);
	}

	public Vector2D clone() {
    	return new Vector2D(componentX, componentY);
	}

	public void scale(float factor) {
    	this.componentX *= factor;
    	this.componentY *= factor;
	}

	@Override
	public String toString() {
		return "(" + this.componentX + ", " + this.componentY + ")";
	}
}
