package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

public class SecondGame extends ApplicationAdapter {
	private Cannon cannon;

	@Override
	public void create() {
		GameEnvironment.init();
		RectangleGraphics.create(GameEnvironment.positionLoc);
		CircleGraphics.create(GameEnvironment.positionLoc);

		cannon = new Cannon(new Point2D(200, 200), GameEnvironment.positionLoc);
		cannon.rotate(new Angle2D(360));
	}

	private void update() {
		float deltaTime = Gdx.graphics.getDeltaTime();

		if(Gdx.input.justTouched()) {

		}

		// Moving cannon

		float changeX, changeY;

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
		cannon.move();


		// Rotating cannon

		if (Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
			cannon.rotate(new Angle2D(150 * deltaTime));
		} else if (!Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			cannon.rotate(new Angle2D(-150 * deltaTime));
		}
	}

	private void display() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		cannon.draw();
	}

	@Override
	public void render() {
		update();
		display();
	}
}