package SASLib.Geom;
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
Quadrilateral
Purpose:
provide general support for a 4-gon
Coders :
Wil Cecil
Created:
Monday, Feb 11th 2008
Change Log: 
 */
import SASLib.Header;
import SASLib.Util.RotMath;
import java.awt.Rectangle;

/**
 * Provide general support for any 4-gon
 * @author Wil Cecil
 */
public class Quadrilateral implements Shape, Cloneable {

    /**
     * these is an array of length 4 that holds all the corner points of this shape.
     */
    Point[] points;
    /**
     * this is the bounding box to be used for this object
     */
    Rectangle bounds;
    /**
     * this is the distance from the middle of the rhombus to the farthest point
     */
    float longestDist;
    /**
     * this is the center as a point
     */
    Point center;

    /**
     * Creates a rhombus from points
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     */
    public Quadrilateral(Point p1, Point p2, Point p3, Point p4) {
        points = new Point[4];
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        points[3] = p4;

        generateBounds();
    }

    /**
     * Creates a rhombus from a rectagle rotated around the origin
     * @param rect
     * @param angle
     */
    public Quadrilateral(Rectangle rect, double angle) {
        this(rect, angle, false);
    }

    /**
     * Creates a rhombus from a rectagle rotated around the origin
     * @param rect
     * @param angle
     */
    public Quadrilateral(Rectangle rect, double angle, boolean aroundCenter) {
        points = new Point[4];

        if (aroundCenter) {
            points[0] = RotMath.RotatePoint(
                    new Point(rect.getX(), rect.getY()), angle);
            points[1] = RotMath.RotatePoint(
                    new Point(rect.getX(), rect.getY() + rect.getHeight()), angle);
            points[2] = RotMath.RotatePoint(
                    new Point(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight()), angle);
            points[3] = RotMath.RotatePoint(
                    new Point(rect.getX() + rect.getWidth(), rect.getY()), angle);
        } else {
            points[0] =
                    new Point(rect.getX(), rect.getY());
            points[1] =
                    new Point(rect.getX(), rect.getY() + rect.getHeight());
            points[2] =
                    new Point(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
            points[3] =
                    new Point(rect.getX() + rect.getWidth(), rect.getY());

            //calculate center
            calcCenter();

            //rotate point.
            rotate(angle);
        }
        generateBounds();
    }
    
    /**
     * Creates a quad from a quad rotated around the origin or middle of quad
     * @param q
     * @param angle
     */
    public Quadrilateral(Quadrilateral q, double angle, boolean aroundCenter) {
        points = new Point[4];

        if (aroundCenter) {
            points[0] = RotMath.RotatePoint(
                    q.points[0], angle);
            points[1] = RotMath.RotatePoint(
                    q.points[1], angle);
            points[2] = RotMath.RotatePoint(
                    q.points[2], angle);
            points[3] = RotMath.RotatePoint(
                    q.points[3], angle);
        } else {
            points[0] =
                    (Point) q.points[0].clone();
            points[1] =
                    (Point) q.points[1].clone();
            points[2] =
                    (Point) q.points[2].clone();
            points[3] =
                    (Point) q.points[3].clone();

            //calculate center
            calcCenter();

            //rotate point.
            rotate(angle);
        }
        generateBounds();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * this method creates the bounding box and bounding circle
     */
    private void generateBounds() {
        //variable dec
        //used for math.max
        longestDist = Float.MIN_VALUE;

        //used to calc center of rhombus
        double x = 0, y = 0;

        //set bounds to initial bounding box
        bounds = new Rectangle(points[0].getBigX(), points[0].getBigY(), 0, 0);

        //loop through all points
        for (int i = 0; i < points.length; i++) {
            //set the current point to points[i]
            Point curr = points[i];

            //adds point to bounding box
            bounds.add(curr.getBigX(), curr.getBigY());

            //used to calc center
            x += curr.getX();
            y += curr.getY();
        }

        //calc center
        x /= points.length;
        y /= points.length;

        center = new Point(x, y);

        //check distances from center
        for (int k = 0; k < points.length; k++) {
            //check if longest dist
            longestDist = Math.max(longestDist, (float) center.distanceSq(points[k]));
        }

        longestDist = (float) Math.sqrt(longestDist);

        if (Header._DEBUG) {
            System.out.println(longestDist);
        }

    }

    /**
     * this method only calculates the center of the shape.
     */
    private void calcCenter() {
        //used to calc center of rhombus
        double x = 0, y = 0;

        //loop through all points
        for (int i = 0; i < points.length; i++) {
            //set the current point to points[i]
            Point curr = points[i];

            //used to calc center
            x += curr.getX();
            y += curr.getY();
        }

        //calc center
        x /= points.length;
        y /= points.length;

        center = new Point(x, y);
    }

    /**
     * returns the nth point 
     * @param n
     * @return the nth point on the shape
     */
    public Point getPoint(int n) {
        return points[n];
    }

    /**
     * this returns the center of the shape
     * @return center point
     */
    public Point getCenter() {
        return center;
    }

    /**
     * this returns the distance from the center to the farthest most point.
     * @return a float with the longest distance.
     */
    public float getLongestDist() {
        return longestDist;
    }

    /**
     * returns all the points in the shape as a one dim array
     * @return Point[]
     */
    public Point[] getPoints() {
        return points;
    }

    /**
     * this method rotates the object around the center by angle
     * @param angle
     */
    public void rotate(double angle) {
        for (int i = 0; i < points.length; i++) {
            points[i] = RotMath.RotatePoint(
                    points[i], center, angle);
        }
    }
}
