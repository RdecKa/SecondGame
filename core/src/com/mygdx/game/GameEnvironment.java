package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;
import java.nio.FloatBuffer;
import java.util.Vector;

public class GameEnvironment {

    public static FloatBuffer modelMatrix;
    public static int modelMatrixLoc;
    public static int projectionMatrixLoc;
    public static FloatBuffer projectionMatrix;
    public static int renderingProgramID;
    public static int vertexShaderID;
    public static int fragmentShaderID;
    public static int positionLoc;
    public static int colorLoc;
    public static float winWidth, winHeight;
    public static String state;
    public static int numLevels, curLevelIndex;
    public static Level[] levels;

    public static void init() {
        String vertexShaderString;
        String fragmentShaderString;

        vertexShaderString = Gdx.files.internal("shaders/simple2D.vert").readString();
        fragmentShaderString =  Gdx.files.internal("shaders/simple2D.frag").readString();

        vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
        Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

        Gdx.gl.glCompileShader(vertexShaderID);
        Gdx.gl.glCompileShader(fragmentShaderID);

        renderingProgramID = Gdx.gl.glCreateProgram();

        Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
        Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);

        Gdx.gl.glLinkProgram(renderingProgramID);

        positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
        Gdx.gl.glEnableVertexAttribArray(positionLoc);

        modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
        projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

        colorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

        Gdx.gl.glUseProgram(renderingProgramID);

        float[] pm = new float[16];

        pm[0] = 2.0f / Gdx.graphics.getWidth(); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -1.0f;
        pm[1] = 0.0f; pm[5] = 2.0f / Gdx.graphics.getHeight(); pm[9] = 0.0f; pm[13] = -1.0f;
        pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 1.0f; pm[14] = 0.0f;
        pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

        projectionMatrix = BufferUtils.newFloatBuffer(16);
        projectionMatrix.put(pm);
        projectionMatrix.rewind();
        Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, projectionMatrix);


        float[] mm = new float[16];

        mm[0] = 1.0f; mm[4] = 0.0f; mm[8] = 0.0f; mm[12] = 0.0f;
        mm[1] = 0.0f; mm[5] = 1.0f; mm[9] = 0.0f; mm[13] = 0.0f;
        mm[2] = 0.0f; mm[6] = 0.0f; mm[10] = 1.0f; mm[14] = 0.0f;
        mm[3] = 0.0f; mm[7] = 0.0f; mm[11] = 0.0f; mm[15] = 1.0f;

        modelMatrix = BufferUtils.newFloatBuffer(16);
        modelMatrix.put(mm);
        modelMatrix.rewind();

        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);

        winWidth = Gdx.graphics.getWidth();
        winHeight = Gdx.graphics.getHeight();

        numLevels = 4;

        levels = new Level[numLevels];
        for (int i = 0; i < numLevels; i++) {
        	levels[i] = new Level(i + 1);
		}

		RectangleGraphics.create(positionLoc);
		CircleGraphics.create(positionLoc);
		LineGraphics.create(positionLoc);
    }

    public static void resetGame() {
		state = "start";
		curLevelIndex = 0;
	}

    public static void clearModelMatrix() {
        modelMatrix.put(0, 1.0f);
        modelMatrix.put(1, 0.0f);
        modelMatrix.put(2, 0.0f);
        modelMatrix.put(3, 0.0f);
        modelMatrix.put(4, 0.0f);
        modelMatrix.put(5, 1.0f);
        modelMatrix.put(6, 0.0f);
        modelMatrix.put(7, 0.0f);
        modelMatrix.put(8, 0.0f);
        modelMatrix.put(9, 0.0f);
        modelMatrix.put(10, 1.0f);
        modelMatrix.put(11, 0.0f);
        modelMatrix.put(12, 0.0f);
        modelMatrix.put(13, 0.0f);
        modelMatrix.put(14, 0.0f);
        modelMatrix.put(15, 1.0f);
        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
    }

    public static void setModelMatrixTranslation(float xTranslate, float yTranslate) {
        modelMatrix.put(12, xTranslate);
        modelMatrix.put(13, yTranslate);
        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
    }

    public static void setModelMatrixScale(float xScale, float yScale) {
        modelMatrix.put(0, xScale);
        modelMatrix.put(5, yScale);
        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
    }
}

class Level {

	private int levelNum;
	private Vector<Obstacle> obstacles;
	public Line2D drawingLine;

	public Level(int levelNum) {
		this.levelNum = levelNum;
		reset();
	}

	public void reset() {
		this.obstacles = new Vector<Obstacle>();
		switch (levelNum) {
			case 1: {
				float boxSize = 100;
				obstacles.add(new Box(boxSize, 0.5f, 0.5f, 0.5f, 1, new Point2D(500, 300)));
				break;
			}
			case 2: {
				float boxSize = 80;
				obstacles.add(new Box(boxSize, 0.5f, 0.5f, 0.5f, 1, new Point2D(600, 200)));
				obstacles.add(new Box(boxSize, 0.5f, 0.5f, 0.5f, 1, new Point2D(100, 400)));
				break;
			}
			case 3: {
				float boxSize = 60;
				obstacles.add(new Box(boxSize, 0.5f, 0.5f, 0.5f, 1, new Point2D(400, 200)));
				obstacles.add(new Box(boxSize, 0.5f, 0.5f, 0.5f, 1, new Point2D(200, 400)));
				obstacles.add(new Box(boxSize, 0.5f, 0.5f, 0.5f, 1, new Point2D(100, 100)));
				break;
			}
			case 4: {
				float boxSize = 50;
				obstacles.add(new Box(boxSize, 0.5f, 0.5f, 0.5f, 1, new Point2D(400, 400)));
				obstacles.add(new Box(boxSize, 0.5f, 0.5f, 0.5f, 1, new Point2D(300, 200)));
				obstacles.add(new Box(boxSize, 0.5f, 0.5f, 0.5f, 1, new Point2D(600, 200)));
				obstacles.add(new Box(boxSize, 0.5f, 0.5f, 0.5f, 1, new Point2D(200, 300)));
				break;
			}
		}
	}

	public void draw() {

		for (Obstacle obst: obstacles) {
			obst.draw();
		}
		if (drawingLine != null) {
			drawingLine.draw();
		}
	}

	public Vector<Obstacle> getObstacles() {
		return this.obstacles;
	}

	public void addObstacle(Obstacle obst) {
		obstacles.add(obst);
	}
}
