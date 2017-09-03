package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Box {
	private float boxSize;
	private float red, green, blue, alpha;
	private Point2D position;

	public Box(float boxSize, float red, float green, float blue, float alpha, Point2D position) {
		this.boxSize = boxSize;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		this.position = position;
	}

	public void draw() {
		GameEnvironment.setModelMatrixTranslation(position.getPositionX(), position.getPositionY());
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, red, green, blue, alpha);
		GameEnvironment.setModelMatrixScale(boxSize, boxSize);
		RectangleGraphics.drawSolidSquare();
	}
}
