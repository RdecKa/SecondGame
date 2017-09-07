package com.mygdx.game;

public class Line2D extends Obstacle {
	private Point2D A1, A2;

	public Line2D(float red, float green, float blue, float alpha, Point2D A1, Point2D A2) {
		super(red, green, blue, alpha);
		this.A1 = A1;
		this.A2 = A2;
	}

	public void draw() {
		GameEnvironment.clearModelMatrix();
		LineGraphics.drawLine(A1, A2);
	}

	public Point2D getA1() {
		return A1;
	}

	public Point2D getA2() {
		return A2;
	}
}
