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
	private float ballRadius;
    private CannonBall ball;

	private static FloatBuffer vertexBuffer;
	private static int vertexPointer;

    public Cannon(Point2D position, int vertexPointer) {
		this.position = position;
		this.speed = new Vector2D(0, 0);
        this.acceleration = new Vector2D(20, 20);
        this.angle = new Angle2D(0);
        this.length = 50;
        this.width = 18;
        this.circleRadius = 10;
        this.ball = null;
        this.ballRadius = 8;

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

		// CannonBall
		if (ball != null) {
			ball.draw();
		}

		GameEnvironment.setModelMatrixTranslation(position.getPositionX(), position.getPositionY());

		// Circle
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0, 0, 0, 1);
		GameEnvironment.setModelMatrixScale(2 * circleRadius, 2 * circleRadius);
		CircleGraphics.drawSolidCircle();

		// Body
		GameEnvironment.setModelMatrixScale(1,1);
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0, 0, 0, 1);
		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, 4);
	}

    public void move() {
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

	public void rotate(Angle2D angle) {
		this.angle.add(angle);

		// Angle from center to distant corners
		double tanAngleDelta = (width / 2) / length;
		Angle2D angleDelta = new Angle2D((float)(Math.toDegrees(Math.atan(tanAngleDelta))));

		Angle2D angBotLef = this.angle.getPerpAngle(90);
		Angle2D angBotRig = this.angle.getPerpAngle(-90);
		Angle2D angTopLef = new Angle2D(this.angle.getAngleDegrees() + angleDelta.getAngleDegrees());
		Angle2D angTopRig = new Angle2D(this.angle.getAngleDegrees() - angleDelta.getAngleDegrees());

		float distance = (float)(Math.sqrt((width / 2) * (width / 2) + length * length));

		Point2D botLef = new Point2D((float)(Math.cos(angBotLef.getAngleRadians()) * width / 2),
									 (float)(Math.sin(angBotLef.getAngleRadians()) * width / 2));
		Point2D botRig = new Point2D((float)(Math.cos(angBotRig.getAngleRadians()) * width / 2),
									 (float)(Math.sin(angBotRig.getAngleRadians()) * width / 2));
		Point2D topLef = new Point2D((float)(Math.cos(angTopLef.getAngleRadians()) * distance),
									 (float)(Math.sin(angTopLef.getAngleRadians()) * distance));
		Point2D topRig = new Point2D((float)(Math.cos(angTopRig.getAngleRadians()) * distance),
									 (float)(Math.sin(angTopRig.getAngleRadians()) * distance));

		float[] array = {
				botLef.getPositionX(), botLef.getPositionY(),
				topLef.getPositionX(), topLef.getPositionY(),
				topRig.getPositionX(), topRig.getPositionY(),
				botRig.getPositionX(), botRig.getPositionY()};

		vertexBuffer.put(array);
		vertexBuffer.rewind();
	}

	public CannonBall fire() {
		if (this.ball == null) {
			float ballStartPositionX = (float) (position.getPositionX() + Math.cos(angle.getAngleRadians()) * length);
			float ballStartPositionY = (float) (position.getPositionY() + Math.sin(angle.getAngleRadians()) * length);
			this.ball = new CannonBall(ballRadius, new Point2D(ballStartPositionX, ballStartPositionY), angle);
			GameEnvironment.state = "ballfired";
		}
		return this.ball;
	}

	public void moveBall() {
    	this.ball.move();
	}

	public void reset() {
    	ball = null;
    	speed = new Vector2D(0, 0);
	}

	public Vector2D getAcceleration() { return acceleration; }

	public CannonBall getBall() { return ball; }

	public void spinOut(Angle2D anglePerFrame, Vector2D motion) {
		// Called when level is finished
		position.move(motion);
		rotate(anglePerFrame);
	}

	public boolean outOfWindow() {
    	return (position.getPositionX() < - 2 * length ||
				position.getPositionX() > GameEnvironment.winWidth + 2 * length||
				position.getPositionY() < - 2 * length ||
				position.getPositionY() > GameEnvironment.winHeight + 2 * length);
	}
}

class CannonBall {
	private float ballRadius;
	private Point2D position;
	private Vector2D motion;

	public CannonBall(float ballRadius, Point2D position, Angle2D angle) {
		this.ballRadius = ballRadius;
		this.position = position;
		this.motion = angle.toVector(10);
	}

	public void draw() {
		GameEnvironment.setModelMatrixTranslation(position.getPositionX(), position.getPositionY());
		Gdx.gl.glUniform4f(GameEnvironment.colorLoc, 0, 0.7f, 0.2f, 1);
		GameEnvironment.setModelMatrixScale(ballRadius, ballRadius);
		CircleGraphics.drawSolidCircle();
	}

	public void move() {
		Obstacle nearest;
		while ((nearest = nearestObstacleInThisFrame()) != null) {
			if (nearest instanceof Box)
				GameEnvironment.levels[GameEnvironment.curLevelIndex].removeObstacle(nearest);
		}
		position.move(motion);
		if (GameEnvironment.state.equals("ballfired") &&
				(position.getPositionX() < - 2 * ballRadius ||
				position.getPositionX() > GameEnvironment.winWidth + 2 * ballRadius ||
				position.getPositionY() < - 2 * ballRadius ||
				position.getPositionY() > GameEnvironment.winHeight + 2 * ballRadius)) {
			GameEnvironment.state = "gameover";
		}
	}

	private Obstacle nearestObstacleInThisFrame() {
		// Returns the nearest obstacle
		// Also sets reflection vector
		// And checks whether the game is over (no boxes left)
		Obstacle nearest = null;
		int numBoxesLeft = 0;
		float smallestThit = Integer.MAX_VALUE;
		for (Obstacle obst: GameEnvironment.levels[GameEnvironment.curLevelIndex].getObstacles()) {
			float smallestThitThisObstacle = Integer.MAX_VALUE;
			Point2D B1 = null;
			Point2D B2 = null;
			if (obst instanceof Box) {
				numBoxesLeft++;

				Point2D boxPosition = ((Box) obst).getPosition();
				float boxPositionX = boxPosition.getPositionX();
				float boxPositionY = boxPosition.getPositionY();
				float halfBoxSize = ((Box) obst).getBoxSize() / 2;

				Point2D[] vertices = new Point2D[4];
				vertices[0] = new Point2D(boxPositionX - halfBoxSize, boxPositionY - halfBoxSize);
				vertices[1] = new Point2D(boxPositionX - halfBoxSize, boxPositionY + halfBoxSize);
				vertices[2] = new Point2D(boxPositionX + halfBoxSize, boxPositionY + halfBoxSize);
				vertices[3] = new Point2D(boxPositionX + halfBoxSize, boxPositionY - halfBoxSize);

				for (int i = 0; i < vertices.length; i++) {
					float ThitTmp = getThit(vertices[i], vertices[(i + 1) % vertices.length], position, motion);
					if (ThitTmp >= 0 && ThitTmp <= 1) {
						// Is the collision happening inside this frame?
						Point2D pHit = getPhit(position, ThitTmp, motion);

						if (pHit != null && pHit.isBetween(vertices[i], vertices[(i + 1) % vertices.length]) && ThitTmp < smallestThitThisObstacle) {
							smallestThitThisObstacle = ThitTmp;
							B1 = vertices[i];
							B2 = vertices[(i + 1) % vertices.length];
						}
					}
				}
			} else if (obst instanceof Line2D) {
				Line2D line = (Line2D) obst;
				Point2D P1 = line.getA1();
				Point2D P2 = line.getA2();
				float ThitTmp = getThit(P1, P2, position, motion);
				if (ThitTmp >= 0 && ThitTmp <= 1) {
					Point2D pHit = getPhit(position, ThitTmp, motion);
					if (pHit != null && pHit.isBetween(P1, P2)) {
						smallestThitThisObstacle = ThitTmp;
						B1 = P1;
						B2 = P2;
					}
				}
			}
			if (smallestThitThisObstacle < smallestThit) {
				smallestThit = smallestThitThisObstacle;
				nearest = obst;
				motion = getReflectedVector(B1, B2, motion);
			}
		}
		if (numBoxesLeft == 0) {
			GameEnvironment.state = "win";
		}
		return nearest;
	}

	private static float getThit(Point2D B1, Point2D B2, Point2D A, Vector2D c) {
		// Calculates time when point A travelling in direction c hits the line between points B1 and B2.
		Vector2D v = B1.vectorFromHereToPoint(B2);
		Vector2D n = v.getPerpendicularVector();
		Vector2D ab = A.vectorFromHereToPoint(B1);
		float tHit = Vector2D.dotProduct(n, ab) / Vector2D.dotProduct(n, c);
		return tHit;
	}

	private static Point2D getPhit(Point2D A, float tHit, Vector2D c) {
		// Calculates Phit = A + tHit * c
		Point2D pHit = A.clone();
		Vector2D translationVector = c.clone();
		translationVector.scale(tHit);
		pHit.move(translationVector);
		return pHit;
	}

	private static Vector2D getReflectedVector(Point2D B1, Point2D B2, Vector2D c) {
		Vector2D v = B1.vectorFromHereToPoint(B2);
		Vector2D n = v.getPerpendicularVector();
		float an = Vector2D.dotProduct(c, n);
		float nn = Vector2D.dotProduct(n, n);
		Vector2D substractedVector = n.clone();
		substractedVector.scale(- 2 * an / nn);
		Vector2D reflectedVector = c.clone();
		reflectedVector.addVector(substractedVector);
		return reflectedVector;
	}
}