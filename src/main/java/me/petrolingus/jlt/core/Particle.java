package me.petrolingus.jlt.core;

import javafx.scene.paint.Color;

public class Particle {

    public double x;
    public double y;
    public double vx;
    public double vy;

    private Bound bound;

    public Particle(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void setBound(Bound bound) {
        this.bound = bound;
    }

    public void move() {
        x += vx;
        y += vy;
        switch (bound.isOutside(x, y)) {
            case TOP, BOTTOM -> vy *= -1;
            case RIGHT, LEFT -> vx *= -1;
        }
    }

    public Color getColor() {
        double t =  Math.sqrt(vx * vx + vy * vy) / 4.24264068712;
        return Color.GREEN.interpolate(Color.RED, t);
    }

}
