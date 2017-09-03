package com.mygdx.game;

import com.badlogic.gdx.Gdx;

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

    public void move(Point2D point) {
    	this.positionX = point.positionX;
    	this.positionY = point.positionY;
	}

    public void draw() {
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0, 0, 0, 1);
		GameEnvironment.setModelMatrixTranslation(positionX, positionY);
		GameEnvironment.setModelMatrixScale(10, 10);
    	CircleGraphics.drawSolidCircle();
    	System.out.println("Point");
	}

	public Vector2D pointToVector() {
		return new Vector2D(this.getPositionX(), this.getPositionY());
	}

    @Override
    public String toString() {
        return "(" + positionX + ", " + positionY + ")";
    }
}
