package naturalselection;

import java.awt.*;

public class Vector implements Cloneable {
    public double x, y;
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector toCartesian(double magnitude, double theta) {
        return new Vector(toX(magnitude, theta), toY(magnitude, theta));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getMagnitude() {
        return toMagnitude(x, y);
    }

    public void setMagnitude(double magnitude) {
        double theta = toAngle(x, y);
        this.x = toX(magnitude, theta);
        this.y = toY(magnitude, theta);
    }

    public double getAngle() {
        return toAngle(x, y);
    }

    public void setAngle(double theta) {
        double magnitude = toMagnitude(x, y);
        this.x = toX(magnitude, theta);
        this.y = toY(magnitude, theta);
    }

    public void setVector(Vector vector) {
        x = vector.x;
        y = vector.y;
    }

    public void setPoint(Point point) {
        x = point.x;
        y = point.y;
    }

    public Point getPoint() {
        return new Point((int) x, (int) y);
    }

    public static double toMagnitude(double x, double y) {
        return Math.hypot(x, y);
    }

    public static double toAngle(double x, double y) {
        return Math.atan(y/x);
    }

    public static double toX(double magnitude, double theta) {
        return magnitude*Math.cos(theta);
    }

    public static double toY(double magnitude, double theta) {
        return magnitude*Math.sin(theta);
    }

    public Vector add(Vector vector) {
        return new Vector(x+vector.x, y+vector.y);
    }

    public Vector subtract(Vector vector) {
        return new Vector(x-vector.x, y-vector.y);
    }

    public Vector multiply(double factor) {
        Vector newVector = new Vector(x, y);
        newVector.setMagnitude(getMagnitude()*factor);
        return newVector;
    }

    public double dot(Vector vector) {
        return getMagnitude() * vector.getMagnitude() * Math.cos(getAngle()-vector.getAngle());
    }

    public double distance(Vector vector) {
        return Math.hypot(x-vector.x, y-vector.y);
    }

    public void draw(Graphics2D g2, int x, int y) {
        g2.drawLine(x, y, (int) (x+this.x), (int) (y+this.y));
    }

    @Override
    public Vector clone() {
        try {
            return (Vector) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
