// Copyright (c) 2005 William Bogg Cecil. All rights reserved. Use is
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
Line
Purpose:
Define a simple class to represent all lines
Coders :
Wil Cecil
Created:
Feb 4th 2008
Change Log: 
 */
package SASLib.Geom;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * This is the simplist of shapes, a single line
 * @author Wil Cecil
 */
public class Line implements Shape, Cloneable {

    /**
     * This is one of two definition points
     */
    Point pnt1;
    /**
     * This is one of two definition points
     */
    Point pnt2;
    /**
     * Strict bound checking
     */
    boolean bounding;

    /**
     * This is a line unbounded 
     * same as Line(pnt1, pnt2, false)
     * @param pnt1
     * @param pnt2
     */
    public Line(Point pnt1, Point pnt2) {
        this(pnt1, pnt2, false);
    }

    /**
     * Forces This line to act as a line segment
     * @param pnt1
     * @param pnt2
     * @param bound Strict Line Segment Checking
     */
    public Line(Point pnt1, Point pnt2, boolean bound) {
        this.pnt1 = pnt1;
        this.pnt2 = pnt2;
        bounding = bound;
    }

    public void setPnt1(Point pnt1) {
        this.pnt1 = pnt1;
    }

    public void setPnt2(Point pnt2) {
        this.pnt2 = pnt2;
    }

    /**
     * Check bounds first (mustg be between pnt1.x to pnt2.x iff bounding
     * then finds diffence between point and line
     * @param point
     * @return double either positive, 0 or negative for GRTR THAN, On Line, LESS THAN
     * @throws java.lang.IllegalArgumentException if point.x is outside of segment bounds
     */
    public double compare(Point2D point) throws IllegalArgumentException {
        return compare(point, !bounding);
    }

    /**
     * Check bounds first (mustg be between pnt1.x to pnt2.x iff bounding
     * then finds diffence between point and line
     * @param point
     * @return double either positive, 0 or negative for GRTR THAN, On Line, LESS THAN
     * @throws java.lang.IllegalArgumentException if point.x is outside of segment bounds
     */
    public double compare(Point2D point, boolean ignoreBounds) throws IllegalArgumentException {
        //y-b = m(x-a) changed to ?= m(x-a)+b-y 
        //shuch that ? is the diffenece between y(point.x) and point.y 
        //Check bounds first (mustg be between pnt1.x to pnt2.x iff bounding
        if (bounding && !ignoreBounds) {
            if (checkBounds(point)) {
                throw new IllegalArgumentException(
                        "Argument point.x must be between " + 
                        Math.min(pnt1.x, pnt2.x) + "and" + 
                        Math.max(pnt1.x, pnt2.x) + ".");
            }
        }
        double value;
        try {
            value = evatulate(point.getX()) - point.getY();
        } catch (Exception e) {
            value = Double.MAX_VALUE*(point.getX() - pnt1.x) + pnt1.y - point.getY();
        }


        //neg value to make grtr than positive.
        return -value;
    }

    /**
     * Return the slope of a line
     * @return double   Slope of line
     * @throws java.lang.ArithmeticException
     */
    public double getSlope() throws ArithmeticException {
        if (pnt1.getX() - pnt2.getX() != 0) {
            return ((pnt1.getY() - pnt2.getY()) / (pnt1.getX() - pnt2.getX()));
        } else {
            throw new ArithmeticException("Division by zero, deltax = 0");
        }
    }

    /**
     * Evaluates the y value for this point on the line.
     * @param x
     * @return ( x, f(x) )
     * @throws java.lang.ArithmeticException
     */
    public double evatulate(double x) throws ArithmeticException {
        return (getSlope() * (x - pnt1.x)) + pnt1.y;
    }

    /**
     * Checks is within the x bounds. This is is faster than rectangle bound checking.
     * @param point
     * @return boolean true if x is within constraints
     */
    public boolean checkBounds(Point2D point) {
        return point.getX() <= Math.max(pnt1.x, pnt2.x) && point.getX() >= Math.min(pnt1.x, pnt2.x);
    }

    /**
     * Checks is within the x bounds. This is is faster than rectangle bound checking.
     * @param pointx
     * @return boolean true if x is within constraints
     */
    public boolean checkBounds(double pointx) {
        return pointx <= Math.max(pnt1.x, pnt2.x) && pointx >= Math.min(pnt1.x, pnt2.x);
    }
    
    /**
     * Checks is within the y bounds. This is is faster than rectangle bound checking.
     * @param point
     * @return boolean true if x is within constraints
     */
    public boolean checkBoundsy(Point2D point) {
        return point.getY() <= Math.max(pnt1.y, pnt2.y) && point.getY() >= Math.min(pnt1.y, pnt2.y);
    }

    /**
     * Checks is within the x bounds. This is is faster than rectangle bound checking.
     * @param pointy
     * @return boolean true if x is within constraints
     */
    public boolean checkBoundsy(double pointy) {
        return pointy <= Math.max(pnt1.y, pnt2.y) && pointy >= Math.min(pnt1.y, pnt2.y);
    }

    public Rectangle getBounds() {
        Rectangle boundline;

        boundline = new Rectangle(pnt1.getBigX(), pnt1.getBigY());
        boundline.add(pnt2.getBigX(), pnt2.getBigY());

        return boundline;
    }

    public Point getPnt1() {
        return pnt1;
    }

    public Point getPnt2() {
        return pnt2;
    }
}
