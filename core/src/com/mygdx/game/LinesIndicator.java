package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class LinesIndicator extends Indicator {
	private int linesAvailable;
	private Point2D position;
	private float interval;
	private float lineLength;
	private float lineWidth;

	public LinesIndicator(float red, float green, float blue, float alpha, int linesAvailable) {
		super(red, green, blue, alpha);
		this.linesAvailable = linesAvailable;
		position = new Point2D(0, GameEnvironment.winHeight - 20);
		interval = 10;
		lineLength = 29;
		lineWidth = 3;
	}

	public void removeLine() {
		linesAvailable--;
	}

	public void draw() {
		for (int i = 1; i <= linesAvailable; i++) {
			GameEnvironment.setModelMatrixTranslation(position.getPositionX() + i * interval, position.getPositionY());
			Gdx.gl.glUniform4f(GameEnvironment.colorLoc, red, green, blue, alpha);
			GameEnvironment.setModelMatrixScale(lineWidth, lineLength);
			RectangleGraphics.drawSolidSquare();
		}
	}
}
