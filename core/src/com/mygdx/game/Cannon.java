package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class Cannon {

    private Point2D position;
    private Vector2D speed;
    private Vector2D acceleration;
    private Angle2D angle;
    private float length;
    private float width;
    private float circleRadius;

	private static FloatBuffer vertexBuffer;
	private static int vertexPointer;

    public Cannon(Point2D position, int vertexPointer) {
        this.position = position;
        this.speed = new Vector2D(0, 0);
        this.acceleration = new Vector2D(50, 50);
        this.angle = new Angle2D(0);
        this.length = 50;
        this.width = 10;
        this.circleRadius = 10;

		Cannon.vertexPointer = vertexPointer;

		float[] array = {
				-0.5f * width, 0,
				-0.5f * width, length,
				 0.5f * width, length,
				 0.5f * width, 0};

		vertexBuffer = BufferUtils.newFloatBuffer(8);
		vertexBuffer.put(array);
		vertexBuffer.rewind();
    }

    public void draw() {
		GameEnvironment.setModelMatrixTranslation(position.getPositionX(), position.getPositionY());

		// Circle - outer, black
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0, 0, 0, 1);
		GameEnvironment.setModelMatrixScale(2 * circleRadius, 2 * circleRadius);
		CircleGraphics.drawSolidCircle();
		// Circle - inner, white
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 1, 1, 1, 1);
		GameEnvironment.setModelMatrixScale(1.5f * circleRadius, 1.5f * circleRadius);
		CircleGraphics.drawSolidCircle();

		// Body - white
		GameEnvironment.setModelMatrixScale(1.9f,1.9f);
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 1, 1, 1, 1);
		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, 4);
		// Body - black
		GameEnvironment.setModelMatrixScale(1,1);
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0, 0, 0, 1);
		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, 4);
	}

    public void move(float deltaTime) {
		if (position.getPositionX() + speed.getComponentX() < 2 * circleRadius) {
			// Check left border
			speed.setComponentX(0);
			position.setPositionX(2 * circleRadius);
		} else if (position.getPositionX() + speed.getComponentX() > GameEnvironment.winWidth - 2 * circleRadius) {
			// Check right border
			speed.setComponentX(0);
			position.setPositionX(GameEnvironment.winWidth - 2 * circleRadius);
		} else {
			position.move(speed);
		}

		if (position.getPositionY() + speed.getComponentY() < 2 * circleRadius) {
			// Check bottom border
			speed.setComponentY(0);
			position.setPositionY(2 * circleRadius);
		} else if (position.getPositionY() + speed.getComponentY() > GameEnvironment.winHeight - 2 * circleRadius) {
			// Check top border
			speed.setComponentY(0);
			position.setPositionY(GameEnvironment.winHeight - 2 * circleRadius);
		} else {
			position.move(speed);
		}
    }

    public void changeSpeed(float deltaX, float deltaY, float deltaTime) {
    	if (deltaX == Integer.MAX_VALUE) {
    		// Left and right arrow keys not pressed (or both pressed)
			if (speed.getComponentX() < 0) {
				// Moving left
				deltaX = acceleration.getComponentX();
				if (speed.getComponentX() + deltaX * deltaTime >= 0) {
					deltaX = 0;
					speed.setComponentX(0);
				}
			} else {
				// Moving right
				deltaX = - acceleration.getComponentX();
				if (speed.getComponentX() + deltaX * deltaTime <= 0) {
					deltaX = 0;
					speed.setComponentX(0);
				}
			}
		}

		if (deltaY == Integer.MAX_VALUE) {
			// Up and down arrow keys not pressed (or both pressed)
			if (speed.getComponentY() < 0) {
				// Moving down
				deltaY = acceleration.getComponentY();
				if (speed.getComponentY() + deltaY * deltaTime >= 0) {
					deltaY = 0;
					speed.setComponentY(0);
				}
			} else {
				// Moving up
				deltaY = - acceleration.getComponentY();
				if (speed.getComponentY() + deltaY * deltaTime <= 0) {
					deltaY = 0;
					speed.setComponentY(0);
				}
			}
		}
		speed.changeComponents(deltaX * deltaTime, deltaY * deltaTime);
	}

	public Vector2D getAcceleration() {
		return acceleration;
	}
}
