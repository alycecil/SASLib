/*
 * Point.java
 * 
 * Created on Jan 7, 2008, 2:02:28 PM
 */

package SASLib.Geom;

import java.awt.geom.Point2D;

/**
 * This is a double precision Point2D
 * @author Wil Cecil
 */
public class Point extends Point2D implements Cloneable{
    public double y;
    public double x;

    public Point(double x, double y) {
        this.y = y;
        this.x = x;
    }

    public Point() {
        this.y = 0;
        this.x = 0;
    }
    
    /**
     * Creates a Clone of this point, Translates that point 
     * by the parameter point then returns the new point.
     * @param trans (amount to move (x,y))
     * @return Point
     */
    public Point translate(Point trans){
        Point p = (Point) this.clone();
        p.x+=trans.getX();
        p.y+=trans.getY();
        return p;
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    /**
     * Returns (int) Math.round(x);
     * @return int
     */
    public int getBigX() {
        return (int) Math.round(x);
    }

    /**
     * Returns (int) Math.round(y);
     * @return int
     */
    public int getBigY() {
        return (int) Math.round(y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * throurough description of point
     * @return super.toString()+" ["+x+", "+y+"] hash: "+hashCode()
     */
    @Override
    public String toString() {
        return super.toString()+" ["+x+", "+y+"] hash: "+hashCode();
    }
    
    /**
     * brief description of point
     * @return " ["+x+", "+y+"] "
     */
    public String toStringShort() {
        return " ["+x+", "+y+"] ";
    }

    /**
     * Returns this point as a vector
     * @return Vector
     */
    public Vector asVector(){
        return new Vector(x,y);
    }
}
