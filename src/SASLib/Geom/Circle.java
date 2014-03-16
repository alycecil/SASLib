// Copyright  2005 William Bogg Cecil. All rights reserved. Use is
// subject to license terms.
// 
// This program is free software; you can redistribute it and/or modify
// it under the terms of the Lesser GNU General Public License as
// published by the Free Software Foundation; either version 2 of the
// License, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
// USA

// This code was originally written and compiled on the personal computer
// owned and operated by William Bogg Cecil and no other party may claim 
// ownership of the original code without written consent of William Bogg 
// Cecil. 

// Code maintained by SAS. Students Against Segregation.  
/**	Title:
    Cirlce
Purpose:
    Define a simple class to handle moving cirles
Coders :
    Wil Cecil
Created:
    Feb 6th 2008
Change Log: 
 */
package SASLib.Geom;

import java.awt.Rectangle;

/**
 * This is a moiving circle
 * 
 * @author Wil Cecil
 */
public class Circle implements Cloneable, Shape{

    Point center;
    double radius;
    double mass;
    Vector moveVec;

    public Circle(Point center, double radius, double mass, Vector moveVec) {
        this.center = center;
        this.radius = radius;
        this.mass = mass;
        this.moveVec = moveVec;
    }

    public Circle(Point center, double radius, Vector moveVec) {
        this.center = center;
        this.radius = radius;
        this.mass = radius;
        this.moveVec = moveVec;
    }

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
        this.mass = radius;
        this.moveVec = new Vector();
    }

    
    /**
     * Calculates the bounce/rebound of two coliding circles
     * @param circle1
     * @param circle2
     * @param v1
     * @param v2
     */
    public static void bounce(Circle circle1, Circle circle2, Vector v1, Vector v2) {
        // First, find the normalized vector n from the center of
        // circle1 to the center of circle2
        Vector n = circle1.center.asVector().minus(circle2.center.asVector());
        n.normalize();

        // Find the length of the component of each of the movement
        // vectors along n.
        // a1 = v1 . n
        // a2 = v2 . n
        double a1 = v1.dot(n);
        double a2 = v2.dot(n);

        // Using the optimized version,
        // optimizedP =  2(a1 - a2)
        //              -----------
        //                m1 + m2
        double optimizedP = (2.0 * (a1 - a2)) / (circle1.mass + circle2.mass);

        // Calculate v1', the new movement vector of circle1
        // v1' = v1 - optimizedP * m2 * n
        Vector v1prime = v1.minus(n.times(optimizedP * circle2.mass));

        // Calculate v1', the new movement vector of circle1
        // v2' = v2 + optimizedP * m1 * n
        Vector v2prime = v2.minus(n.times(optimizedP * circle1.mass));

        //Set Movement Vectors
        circle1.setMovementVector(v1prime);
        circle2.setMovementVector(v2prime);
    }

    /**
     * Sets the movement Vector
     * @param movevec
     */
    public void setMovementVector(Vector movevec) {
        moveVec = movevec;
    }

    /**
     * Sets the movement Vector, same as <code>setMovementVector(Vector)</code>
     * @param moveVec
     */
    public void setMoveVec(Vector moveVec) {
        this.moveVec = moveVec;
    }

    /**
     * this is an accessor for the movement vector 
     * @return Vector movement vector
     */
    public Vector getMoveVec() {
        return moveVec;
    }

    /**
     * Calculates the relative movement vector relative to another Circle
     * @param B the second circle
     * @return Vector the relative movement vector
     */
    public Vector calcMoveVecRelative(Circle B) {
        Vector v = moveVec.copy();
        v.minus(B.moveVec);
        return v;
    }

    /**
     * Returns the center of this Circle as a Point
     * @return Point
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Returns the radius
     * @return double
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Returns the mass
     * @return double
     */
    public double getMass() {
        return mass;
    }

    /**
     * Sets the center of the circle
     * @param center
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * Sets the radius
     * @param radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Moves the circle by Movement Vector
     */
    public void move() {
        center.translate(moveVec.asPoint());
    }

    /**
     * This creates a rectangle that holds the entire asteriods bounding circle.
     * @return rectangle
     */
    public Rectangle getBounds() {
        return getBounds(center, radius);
    }

    /**
     * This creates a rectangle that holds the entire asteriods bounding circle.
     * @param pnt center of circle
     * @param radius 
     * @return rectangle
     */
    public static Rectangle getBounds(Point pnt, double radius) {
        Rectangle r = new Rectangle((int) (pnt.x - radius+Math.signum(pnt.x - radius)), 
                (int) (pnt.y - radius+Math.signum(pnt.y - radius)),
                (int) radius * 2+1, (int) radius * 2+1);

        return r;
    }
    
    /**
     * Extenral Access to CLONE()
     * @return copy of this obj.
     * @throws java.lang.CloneNotSupportedException
     */
    public Circle copy() throws CloneNotSupportedException{
        
            return (Circle) clone();
        
    }
}
