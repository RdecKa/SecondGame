package com.mygdx.game;

import java.util.Random;

public abstract class Obstacle {
	protected float red, green, blue, alpha;

	public Obstacle(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public void setColor(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public void setRandomColor() {
		Random rand = new Random();
		this.setColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1);
	}

	public abstract void draw();
}
