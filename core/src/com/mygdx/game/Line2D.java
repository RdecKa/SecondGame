package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Line2D extends Obstacle {
	private Point2D A1, A2;
	private float endPointRadius;

	public Line2D(float red, float green, float blue, float alpha, Point2D A1, Point2D A2) {
		super(red, green, blue, alpha);
		this.A1 = A1;
		this.A2 = A2;
		this.endPointRadius = 3;
	}

	public void draw() {
		// Draw line
		GameEnvironment.clearModelMatrix();
		LineGraphics.drawLine(A1, A2);

		// Draw dots on the ends of line
		A1.draw(endPointRadius);
		A2.draw(endPointRadius);
	}

	public Point2D getA1() {
		return A1;
	}

	public Point2D getA2() {
		return A2;
	}
}
