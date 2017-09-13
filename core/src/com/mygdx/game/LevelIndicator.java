package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class LevelIndicator extends Indicator {

	private Point2D position;
	private float interval;
	private float indicatorRadius;
	private int levelNum;

	public LevelIndicator(float red, float green, float blue, float alpha, int levelNum) {
		super(red, green, blue, alpha);
		position = new Point2D(0, 15);
		interval = 15;
		indicatorRadius = 3;
		this.levelNum = levelNum;
	}

	public void draw() {
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, red, green, blue, alpha);
		GameEnvironment.setModelMatrixScale(indicatorRadius * 2, indicatorRadius * 2);

		for (int i = 1; i <= GameEnvironment.numLevels; i++) {
			if (i > levelNum)
				Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0.8f, 0.8f, 0.8f, 1);
			else if (i == levelNum)
				Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0.7f, 0, 0, 1);
			GameEnvironment.setModelMatrixTranslation(position.getPositionX() + i * interval, position.getPositionY());
			CircleGraphics.drawSolidCircle();
		}
	}
}
