package com.mygdx.game;

public class Cannon {

    private Point2D position;

    public Cannon(Point2D position) {
        this.position = position;
    }

    public void move(Vector2D vector) {
        this.position.move(vector);
    }
}
