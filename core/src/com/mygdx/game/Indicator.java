package com.mygdx.game;

public abstract class Indicator {
	protected float red, green, blue, alpha;

	public Indicator(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
}
