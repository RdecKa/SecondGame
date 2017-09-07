package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class LineGraphics {
	private static FloatBuffer vertexBuffer;
	private static int vertexPointer;

	public static void create(int vertexPointer) {
		LineGraphics.vertexPointer = vertexPointer;
		vertexBuffer = BufferUtils.newFloatBuffer(4);
	}

	public static void drawLine(Point2D A1, Point2D A2) {
		vertexBuffer.put(0, A1.getPositionX());
		vertexBuffer.put(1, A1.getPositionY());
		vertexBuffer.put(2, A2.getPositionX());
		vertexBuffer.put(3, A2.getPositionY());
		vertexBuffer.rewind();

		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glDrawArrays(GL20.GL_LINES, 0, 4);
	}
}
