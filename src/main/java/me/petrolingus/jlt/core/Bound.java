package me.petrolingus.jlt.core;

public record Bound(double top, double right, double bottom, double left) {

    public enum Side {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT,
        UNKNOWN
    }

    public Side isOutside(double x, double y) {
        if (y < top) {
            return Side.TOP;
        } else if (x > right) {
            return Side.RIGHT;
        } else if (y > bottom) {
            return Side.BOTTOM;
        } else if (x < left) {
            return Side.LEFT;
        }
        return Side.UNKNOWN;
    }
}
