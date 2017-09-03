package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class Cannon {

    private Point2D position;
    private Angle2D angle;
    private float length;
    private float width;

	private static FloatBuffer vertexBuffer;
	private static int vertexPointer;

    public Cannon(Point2D position, int vertexPointer) {
        this.position = position;
        this.angle = new Angle2D(0);
        this.length = 50;
        this.width = 10;

		Cannon.vertexPointer = vertexPointer;

		float[] array = {
				-0.5f * this.width, 0,
				-0.5f * this.width, this.length,
				 0.5f * this.width, this.length,
				 0.5f * this.width, 0};

		vertexBuffer = BufferUtils.newFloatBuffer(8);
		vertexBuffer.put(array);
		vertexBuffer.rewind();
    }

    public void draw() {
		GameEnvironment.setModelMatrixTranslation(this.position.getPositionX(), this.position.getPositionY());

		// Circle - outer, black
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0, 0, 0, 1);
		GameEnvironment.setModelMatrixScale(20, 20);
		CircleGraphics.drawSolidCircle();
		// Circle - inner, white
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 1, 1, 1, 1);
		GameEnvironment.setModelMatrixScale(15,15);
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

    public void move(Vector2D vector) {
        this.position.move(vector);
    }
}
