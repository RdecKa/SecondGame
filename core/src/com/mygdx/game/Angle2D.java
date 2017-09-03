package com.mygdx.game;

public class Angle2D {
	private float angleDegrees;

	public Angle2D(float angleDegrees) {
		this.angleDegrees = angleDegrees % 360;
	}

	public void add(Angle2D angle) {
		this.angleDegrees = (this.angleDegrees + angle.angleDegrees) % 360;
	}

	public float getAngleDegrees() {
		return angleDegrees;
	}

	public float getAngleRadians() {
		return (float)(Math.toRadians(angleDegrees));
	}

	public Angle2D getPerpAngle(float degrees) {
		return new Angle2D(this.angleDegrees + degrees);
	}

	public Vector2D toVector(float velocity) {
		float componentX = (float)(velocity * Math.cos(Math.toRadians(this.angleDegrees)));
		float componentY = (float)(velocity * Math.sin(Math.toRadians(this.angleDegrees)));
		return new Vector2D(componentX, componentY);
	}
}
