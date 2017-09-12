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

    public void draw(float radius) {
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0, 0, 0, 1);
		GameEnvironment.setModelMatrixTranslation(positionX, positionY);
		GameEnvironment.setModelMatrixScale(2 * radius, 2 * radius);
    	CircleGraphics.drawSolidCircle();
	}

	public Vector2D pointToVector() {
		return new Vector2D(this.getPositionX(), this.getPositionY());
	}

	public Vector2D vectorFromHereToPoint(Point2D p) {
    	float distanceX = p.positionX - this.positionX;
    	float distanceY = p.positionY - this.positionY;
    	return new Vector2D(distanceX, distanceY);
	}

	public Point2D clone() {
    	return new Point2D(positionX, positionY);
	}

	public boolean isBetween(Point2D A, Point2D B) {
    	return ((this.positionX >= A.positionX && this.positionX <= B.positionX ||
				 this.positionX <= A.positionX && this.positionX >= B.positionX) &&
				(this.positionY >= A.positionY && this.positionY <= B.positionY ||
				 this.positionY <= A.positionY && this.positionY >= B.positionY));
	}

    @Override
    public String toString() {
        return "(" + positionX + ", " + positionY + ")";
    }
}
