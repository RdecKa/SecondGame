package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import java.util.Random;

public class Box extends Obstacle {
	private float boxSize;
	private Point2D position;

	public Box(float boxSize, float red, float green, float blue, float alpha, Point2D position) {
		super(red, green, blue, alpha);
		this.boxSize = boxSize;
		this.position = position;
	}

	public void draw() {
		GameEnvironment.setModelMatrixTranslation(position.getPositionX(), position.getPositionY());
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, red, green, blue, alpha);
		GameEnvironment.setModelMatrixScale(boxSize, boxSize);
		RectangleGraphics.drawSolidSquare();
	}

	public void shake() {
		Random rand = new Random();
		position.move(new Point2D(position.getPositionX() + rand.nextFloat() * 4 - 2,
				position.getPositionY() + rand.nextFloat() * 4 - 2));
	}

	public Point2D getPosition() {
		return position;
	}

	public float getBoxSize() {
		return boxSize;
	}

	@Override
	public String toString() {
		return position.toString();
	}
}
