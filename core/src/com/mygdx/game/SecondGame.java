package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

public class SecondGame extends ApplicationAdapter {
	private Cannon cannon;
	private float changeX, changeY;

	@Override
	public void create() {
		GameEnvironment.init();
		RectangleGraphics.create(GameEnvironment.positionLoc);
		CircleGraphics.create(GameEnvironment.positionLoc);

		cannon = new Cannon(new Point2D(200, 200), GameEnvironment.positionLoc);
	}

	private void update() {
		float deltaTime = Gdx.graphics.getDeltaTime();

		if(Gdx.input.justTouched()) {

		}

		// Moving cannon
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			changeX = Integer.MAX_VALUE;
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			changeX = - cannon.getAcceleration().getComponentX();
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			changeX = cannon.getAcceleration().getComponentY();
		} else {
			changeX = Integer.MAX_VALUE;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			changeY = Integer.MAX_VALUE;
		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			changeY = cannon.getAcceleration().getComponentY();
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			changeY = - cannon.getAcceleration().getComponentY();
		} else {
			changeY = Integer.MAX_VALUE;
		}

		cannon.changeSpeed(changeX, changeY, deltaTime);
		cannon.move(deltaTime);
	}

	private void display() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		GameEnvironment.clearModelMatrix();

		cannon.draw();

		/*
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
		*/
	}

	@Override
	public void render() {
		update();
		display();
	}
}