package com.mygdx.game;

import java.nio.FloatBuffer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

public class CircleGraphics {

    private static FloatBuffer vertexBuffer;
    private static int vertexPointer;
    private static int verticesPerCircle;

    public static void create(int vertexPointer) {
        CircleGraphics.vertexPointer = vertexPointer;
        CircleGraphics.verticesPerCircle = 100;

        float[] array = new float[2*verticesPerCircle];

        for (int i = 0; i < 2 * verticesPerCircle; i += 2) {
            double angle = 2 * Math.PI / verticesPerCircle * i;
            array[i] = (float) Math.cos(angle);
            array[i+1] = (float) Math.sin(angle);
        }

        vertexBuffer = BufferUtils.newFloatBuffer(2*verticesPerCircle);
        vertexBuffer.put(array);
        vertexBuffer.rewind();
    }

    public static void drawSolidCircle() {
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, verticesPerCircle);
    }

    public static void drawOutlineCircle() {
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 0, verticesPerCircle);
    }
}