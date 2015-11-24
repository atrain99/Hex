package hex;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Hexagon implements Shape {

    private int xCenter;
    private int yCenter;
    private int radius;

    private int apothem;

    public Hexagon(int x, int y, int r) {
        this.xCenter = x;
        this.yCenter = y;
        this.radius = r;

        double a = 0.87 * r;

        this.apothem = (int) Math.ceil(a);
    }

    public int getRadius() {
        return this.radius;
    }

    public Point2D getCenter() {
        return new Point(this.xCenter, this.yCenter);
    }

    public int getXCenter() {
        return this.xCenter;
    }

    public int getYCenter() {
        return this.yCenter;
    }

    public int getApothem() {
        return this.apothem;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(this.xCenter - this.radius, this.yCenter - this.apothem, 2 * this.radius, 2 * this.apothem);
    }

    @Override
    public Rectangle2D getBounds2D() {
        return getBounds();
    }

    @Override
    public boolean contains(double x, double y) {
        return Math.pow(x - this.xCenter, 2.0D) + Math.pow(y - this.yCenter, 2.0D) < Math.pow(this.radius, 2.0D);
    }

    @Override
    public boolean contains(Point2D p) {
        return p.distanceSq(this.xCenter, this.yCenter) < Math.pow(this.radius, 2.0D);
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean contains(Rectangle2D r) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return new HexagonalPathIterator(this, at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new FlatteningPathIterator(getPathIterator(at), flatness);
    }

}
