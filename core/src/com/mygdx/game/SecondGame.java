package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class SecondGame extends ApplicationAdapter {

	@Override
	public void create() {
		GameEnvironment.init();
		RectangleGraphics.create(GameEnvironment.positionLoc);
		CircleGraphics.create(GameEnvironment.positionLoc);
	}

	private void update() {
		float deltaTime = Gdx.graphics.getDeltaTime();

		if(Gdx.input.justTouched())
		{
			//do mouse/touch input stuff
		}

	}

	private void display() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		// Boxes
		GameEnvironment.setModelMatrixTranslation(100f, 100f);
		GameEnvironment.setModelMatrixScale(100, 100);

		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0.7f, 0.2f, 0, 1);
		RectangleGraphics.drawSolidSquare();

		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0.2f, 0.8f, 0.5f, 1);
		RectangleGraphics.drawOutlineSquare();

		// Circles
		GameEnvironment.setModelMatrixTranslation(500f, 400f);
		GameEnvironment.setModelMatrixScale(90, 50);

		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0f, 0.5f, 0.9f, 1);
		CircleGraphics.drawSolidCircle();

		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0.8f, 0.8f, 0.7f, 1);
		CircleGraphics.drawOutlineCircle();
	}

	@Override
	public void render() {
		update();
		display();
	}
}