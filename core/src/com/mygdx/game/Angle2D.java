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
}
