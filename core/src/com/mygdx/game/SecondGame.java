package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

import javax.sound.sampled.Line;

public class SecondGame extends ApplicationAdapter {
	private Cannon cannon;
	private CannonBall ball;
	private float mouseX, mouseY;
	private Point2D startLine, endLine;

	@Override
	public void create() {
		GameEnvironment.init();

		cannon = new Cannon(new Point2D(20, 200), GameEnvironment.positionLoc);
		cannon.rotate(new Angle2D(360));
		startLine = null;
		endLine = null;
	}

	private void update() {
		float deltaTime = Gdx.graphics.getDeltaTime();

		if (GameEnvironment.state.equals("start") ||
				GameEnvironment.state.equals("ballfired")) {
			if (Gdx.input.justTouched()) {
				mouseX = Gdx.input.getX();
				mouseY = GameEnvironment.winHeight - Gdx.input.getY();
				if (startLine == null) {
					startLine = new Point2D(mouseX, mouseY);
				} else {
					endLine = new Point2D(mouseX, mouseY);
					Line2D newLine = new Line2D(0, 0, 0, 0, startLine, endLine);
					GameEnvironment.levels[GameEnvironment.curLevelIndex].addObstacle(newLine);
					startLine = null;
				}
			}

			// Moving the cannon

			float changeX, changeY;

			if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				changeX = Integer.MAX_VALUE;
			} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				changeX = -cannon.getAcceleration().getComponentX();
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
				changeY = -cannon.getAcceleration().getComponentY();
			} else {
				changeY = Integer.MAX_VALUE;
			}

			cannon.changeSpeed(changeX, changeY, deltaTime);
			cannon.move();


			// Rotating the cannon

			if (Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
				cannon.rotate(new Angle2D(150 * deltaTime));
			} else if (!Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
				cannon.rotate(new Angle2D(-150 * deltaTime));
			}

			// Firing the cannon

			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				ball = cannon.fire();
			}

			// Moving the cannon ball

			if (ball != null) {
				ball.move();
			}
		} else if (GameEnvironment.state.equals("gameover")) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				GameEnvironment.state = "start";
				cannon.reset();
				ball = null;
			}
		}
	}

	private void display() {
		if (GameEnvironment.state.equals("start") ||
				GameEnvironment.state.equals("ballfired")) {
			Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			GameEnvironment.levels[GameEnvironment.curLevelIndex].draw();

			cannon.draw();
		} else {
			Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 0.8f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		}
	}

	@Override
	public void render() {
		update();
		display();
	}
}