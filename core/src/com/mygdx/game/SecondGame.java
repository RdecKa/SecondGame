package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

import java.util.Random;
import java.util.Vector;

public class SecondGame extends ApplicationAdapter {
	private Cannon cannon;
	private float mouseX, mouseY;
	private Point2D startLine, endLine;
	private Point2D cannonStartPosition;
	private Angle2D cannonStartAngle;
	private Angle2D spinOutAngle;
	private Vector2D spinOutVector;
	private Random rand;

	@Override
	public void create() {
		GameEnvironment.init();
		GameEnvironment.resetGame();

		cannonStartPosition = new Point2D(20, 200);
		cannonStartAngle = new Angle2D(0);
		rand = new Random();

		reset();
	}

	private void reset() {
		GameEnvironment.state = "start";
		cannon = new Cannon(cannonStartPosition.clone(), GameEnvironment.positionLoc);
		cannon.rotate(cannonStartAngle);
		startLine = null;
		endLine = null;
		GameEnvironment.levels[GameEnvironment.curLevelIndex].reset();
		spinOutVector = null;
	}

	private void continueToNextLevel() {
		reset();
		GameEnvironment.curLevelIndex++;
		if (GameEnvironment.curLevelIndex >= GameEnvironment.numLevels) {
			GameEnvironment.state = "end";
		}
	}

	private void update() {

		float deltaTime = Gdx.graphics.getDeltaTime();

		if (GameEnvironment.state.equals("start") ||
				GameEnvironment.state.equals("ballfired")) {
			// Disable drawing lines after firing the cannon
			if (GameEnvironment.state.equals("start")) {
				Level curLevel = GameEnvironment.levels[GameEnvironment.curLevelIndex];
				Line2D lineToMove;
				mouseX = Gdx.input.getX();
				mouseY = GameEnvironment.winHeight - Gdx.input.getY();
				Point2D mousePoint = new Point2D(mouseX, mouseY);
				if (Gdx.input.justTouched() && (lineToMove = curLevel.wantToMoveLine(mousePoint)) != null) {
					// User wants to move a line
					Point2D A1 = lineToMove.getA1();
					Point2D A2 = lineToMove.getA2();
					curLevel.removeObstacle(lineToMove);
					if (mousePoint.getDistanceTo(A1) < lineToMove.getEndPointRadius() * 2) {
						// Moving point A1
						startLine = A2;
					} else {
						// Moving point A2
						startLine = A1;
					}
					curLevel.drawingLine = new Line2D(0, 0, 0, 0, startLine, mousePoint);
				} else if (Gdx.input.justTouched() && curLevel.getNumberOfLines() < curLevel.getAllowedNumberOfLines()) {
					// Start of a new line
					if (startLine == null) {
						startLine = new Point2D(mouseX, mouseY);
					}
				} else if (Gdx.input.isTouched() && curLevel.getNumberOfLines() < curLevel.getAllowedNumberOfLines()) {
					// Drawing a line
					endLine = new Point2D(mouseX, mouseY);
					curLevel.drawingLine = new Line2D(0, 0, 0, 0, startLine, endLine);
				} else if (curLevel.getNumberOfLines() < curLevel.getAllowedNumberOfLines()) {
					// End of line
					if (curLevel.drawingLine != null) {
						startLine = null;
						endLine = null;
						curLevel.addObstacle(curLevel.drawingLine);
						curLevel.drawingLine = null;
					}
				}
			}

			// Resetting current level

			if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
				reset();
				return;
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
				cannon.fire();
			}

			// Moving the cannon ball

			if (cannon.getBall() != null) {
				cannon.moveBall();
			}
		} else if (GameEnvironment.state.equals("gameover")) {
			Vector<Box> boxes = GameEnvironment.levels[GameEnvironment.curLevelIndex].getBoxes();
			for (Box box: boxes) {
				box.shake();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				reset();
				GameEnvironment.levels[GameEnvironment.curLevelIndex].reset();
			}
		} else if (GameEnvironment.state.equals("win")) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				continueToNextLevel();
			} else {
				if (spinOutVector == null) {
					float componentX = (rand.nextInt(300) - 150) * deltaTime;
					float sign = rand.nextFloat() >= 0.5 ? -1 : 1;
					float componentY = (float) Math.sqrt(10 - componentX * componentX) * sign;
					spinOutVector = new Vector2D(componentX, componentY);
				}
				spinOutAngle = new Angle2D(150 * deltaTime);
				cannon.spinOut(spinOutAngle, spinOutVector);
				if (cannon.outOfWindow()) {
					continueToNextLevel();
				}
			}

			if (cannon.getBall() != null) {
				cannon.moveBall();
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
		} else if (GameEnvironment.state.equals("gameover")) {
			Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1.0f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			GameEnvironment.levels[GameEnvironment.curLevelIndex].draw();

			cannon.draw();
		} else if (GameEnvironment.state.equals("win")) {
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