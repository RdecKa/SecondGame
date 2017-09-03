package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;
import java.nio.FloatBuffer;

public class RectangleGraphics {

    private static FloatBuffer vertexBuffer;
    private static int vertexPointer;

    public static void create(int vertexPointer) {
        RectangleGraphics.vertexPointer = vertexPointer;

        float[] array = {
                -0.5f, -0.5f,
                -0.5f,  0.5f,
                 0.5f,  0.5f,
                 0.5f, -0.5f};

        vertexBuffer = BufferUtils.newFloatBuffer(8);
        vertexBuffer.put(array);
        vertexBuffer.rewind();
    }

    public static void drawSolidSquare() {
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, 4);
    }

    public static void drawOutlineSquare() {
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 0, 4);
    }
}
