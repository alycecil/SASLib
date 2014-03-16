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
CollisonHandler
Purpose:
A simple single class that handles all posible shape > shape collision detection
Coders :
Wil Cecil
Created:
Feb 16th 2008
Change Log: 
 */
package SASLib.Geom;

import java.awt.Rectangle;

/**
 * This handles collisons between any two SASlib Shapes.
 * 
 * @author Wil Cecil
 */
public class CollisionHandler {

    /**
     * Handles Collisions between any two SAS Lib Shapes
     * @param s1
     * @param s2
     * @return boolean value if the shapes collided
     * @throws java.lang.IllegalArgumentException 
     */
    public synchronized static boolean checkCollision(Shape s1, Shape s2) throws IllegalArgumentException {

        if (s1 instanceof Circle) {
            if (s2 instanceof Circle) {
                return checkCollision((Circle) s1, (Circle) s2);
            }
            if (s2 instanceof Triangle) {
                return checkCollision((Circle) s1, (Triangle) s2);
            }
            if (s2 instanceof Line) {
                return checkCollision((Circle) s1, (Line) s2);
            }
            if (s2 instanceof Quadrilateral) {
                return checkCollision((Circle) s1, (Quadrilateral) s2);
            }
        } else if (s1 instanceof Triangle) {
            if (s2 instanceof Triangle) {
                return checkCollision((Triangle) s1, (Triangle) s2);
            }
            if (s2 instanceof Circle) {
                return checkCollision((Circle) s2, (Triangle) s1);
            }
            if (s2 instanceof Line) {
                return checkCollision((Triangle) s1, (Line) s2);
            }
            if (s2 instanceof Quadrilateral) {
                return checkCollision((Quadrilateral) s1, (Triangle) s2);
            }
        } else if (s1 instanceof Line) {
            if (s2 instanceof Triangle) {
                return checkCollision((Triangle) s2, (Line) s1);
            }
            if (s2 instanceof Circle) {
                return checkCollision((Circle) s2, (Line) s1);
            }
            if (s2 instanceof Line) {
                return checkCollision((Line) s1, (Line) s2);
            }
            if (s2 instanceof Quadrilateral) {
                return checkCollision((Line) s1, (Quadrilateral) s2);
            }
        } else if (s1 instanceof Quadrilateral) {
            if (s2 instanceof Quadrilateral) {
                return checkCollision((Quadrilateral) s1, (Quadrilateral) s2);
            }
            if (s2 instanceof Triangle) {
                return checkCollision((Quadrilateral) s1, (Triangle) s2);
            }
            if (s2 instanceof Circle) {
                return checkCollision((Circle) s2, (Quadrilateral) s1);
            }
            if (s2 instanceof Line) {
                return checkCollision((Line) s2, (Quadrilateral) s1);
            }
        }

        throw new IllegalArgumentException("The Class Combination (" + s1.getClass() + ", " + s2.getClass() + ") is not supported at this time.");
    }

    // <editor-fold defaultstate="collapsed" desc=" Circle with Circle ">  
    /**
     * Moving Circle, Circle collision
     * @param A
     * @param B
     * @return true if a collison occures 
     */
    public synchronized static boolean checkCollision(final Circle A, final Circle B) {
        try {
            //lets create start and end point circles
            Circle Asub1 = (Circle) A.copy();
            Circle Asub2 = (Circle) A.copy();
            Asub2.move(); // calc final

            Circle Bsub1 = (Circle) B.copy();
            Circle Bsub2 = (Circle) B.copy();
            Bsub2.move(); //calc final

//            //lets do a fast bounding box check
//            Rectangle r1 = Asub1.getBounds().union(Asub2.getBounds());
//            Rectangle r2 = Bsub1.getBounds().union(Bsub2.getBounds());
//            if (r1.checkCollision(r2)) {
//                return false;
//            }

            //Actual circle checking
            //Lets check Fast Fails...
            //Start or final colide.
            if (checkColide(Asub1, Bsub1) || checkColide(Asub2, Bsub2)) {
                return true;
            }


            //now lets create our middle point
            Circle BsubM = (Circle) B.copy();
            Circle AsubM = (Circle) A.copy();
            BsubM.setMoveVec(BsubM.getMoveVec().times(0.5));
            BsubM.move();
            AsubM.setMoveVec(AsubM.getMoveVec().times(0.5));
            AsubM.move();

            //Lets check Fast Fails...
            //lets check if middles colides
            if (checkColide(AsubM, BsubM)) {
                return true;
            }

            //
            //We will do the following until either circle A and B colide or
            //Asub1 and Asub2 or Bsub1 and Bsub2 overlap more than a preset ratio
            //
            final double ratio = .60; //Distance between point circles must be at least this much of radius apart
            double step = .25; //this is the amount to multiply the move vec by, halves every iteration
            double dist1 = Asub1.getCenter().distanceSq(AsubM.getCenter());
            double dist2 = Bsub1.getCenter().distanceSq(BsubM.getCenter());
            while (dist1 * ratio > Asub1.getRadius() * Asub1.getRadius() &&
                    dist2 * ratio > Asub2.getRadius() * Asub2.getRadius()) {

                //do actual checks
                //this is between points Asub1 and Bsub1 and then sub2s
                dist1 = Asub1.getCenter().distanceSq(Bsub1.getCenter());
                dist2 = Bsub2.getCenter().distanceSq(Asub2.getCenter());


                //decide which point to move up
                //which ever has bigger distance move up to middle
                if (dist1 > dist2) {
                    //sub 1s are farther apart...
                    Asub1 = AsubM;
                    Bsub1 = BsubM;
                } else {
                    //sub 2s are farther move em together.
                    Asub2 = AsubM;
                    Bsub2 = BsubM;
                }

                //
                //recalculate middles
                //
                BsubM = (Circle) Bsub1.copy();
                AsubM = (Circle) Asub1.copy();
                BsubM.setMoveVec(BsubM.getMoveVec().times(step));
                BsubM.move();
                AsubM.setMoveVec(AsubM.getMoveVec().times(step));
                AsubM.move();

                //
                //check if colision
                //
                if (checkColide(AsubM, BsubM)) {
                    return true;
                }

                //
                //CLEAN UP STEPS FOR NEXT ITERATION
                //redo calc dists
                dist1 = Asub1.getCenter().distanceSq(AsubM.getCenter());
                dist2 = Bsub1.getCenter().distanceSq(BsubM.getCenter());

                //halve step
                step *= .5;
            }


            return false;
        } catch (CloneNotSupportedException ex) {
            return false;
        }
    }

    /**
     * Pss through method to checkColide(Point one, Point two, double rad1, double rad2)
     * @param Asub1
     * @param Bsub1
     * @return true iff they overlap or touch
     */
    private synchronized static boolean checkColide(Circle Asub1, Circle Bsub1) {
        return checkColide(Asub1.getCenter(), Bsub1.getCenter(), Asub1.getRadius(), Bsub1.getRadius());
    }

    /**
     * Does the actual circul distance checks
     * @param one
     * @param two
     * @param rad1
     * @param rad2
     * @return true iff they overlap or touch
     */
    private synchronized static boolean checkColide(Point one, Point two, double rad1, double rad2) {
        //calc dist ^ 2 of center to center2
        double dx, dy;
        dx = one.x - two.x;
        dy = one.y - two.y;
        double distsqrd = dx * dx + dy * dy;

        //check if lessthan or equal to sum of (radius^2)
        if (distsqrd <= rad1 * rad1 + rad1 * rad1) {
            return true;
        }

        return false;
    }
    // </editor-fold>      
    // <editor-fold defaultstate="collapsed" desc=" Circle with Line ">  
    /**
     * This method checks if a line is within or crosses a Circle using algebra 
     * see http://mathworld.wolfram.com/Circle-LineIntersection.html for details.
     * <br>
     * Does not have any false negatives.
     * @param c Circle to check the line with
     * @param l the Line to check if contained
     * @return boolean true if line is within the Circle
     */
    public synchronized static boolean checkCollisionExact(Circle c, Line l) {
        Point pnt1 = l.getPnt1();
        Point pnt2 = l.getPnt2();

        //if endpoints are in that sucker
        //lets get out fast.
        if (contains(c, pnt1) || contains(c, pnt2)) {
            return true;
        }
        //looks like we got some work to do...

        //translate points so circle is around the origin
        pnt1 = pnt1.translate(new Point(-c.getCenter().getX(), -c.getCenter().getY()));
        pnt2 = pnt2.translate(new Point(-c.getCenter().getX(), -c.getCenter().getY()));

        double dx, dy, drSQR, D, discriminate;

        //D = x1y2 - x2y1
        D = pnt1.getX() * pnt2.getY() - pnt2.getX() * pnt1.getY();

        //dx = x2 - x1
        dx = pnt2.getX() - pnt1.getX();

        //dy = y2 - y1
        dy = pnt2.getY() - pnt1.getY();

        //drSQR = (dx^2 + dy^2)
        drSQR = dx * dx + dy * dy;

        //r^2 *dr^2 - D^2
        discriminate = c.getRadius() * c.getRadius() * drSQR - D * D;

        if (SASLib.Header._DEBUG) {
            System.out.println("discriminate " + discriminate);
        }

        //check if line never touches
        if (discriminate < 0) {
            //the intersection points are imaginary
            return false;
        }

        //calculate points
        //x = (  D dy +- sgm(dy) dx SQRT ( discriminate ) ) / drSQR
        //y = ( -D dx +- abs(dx)    SQRT ( discriminate ) ) / drSQR
        if (discriminate == 0) {
            //only one point as +- .... zeros out
            Point tangent = new Point(D * dy / drSQR, -D * dx / drSQR);

            if (SASLib.Header._DEBUG) {
                System.out.println("tangent " + tangent.toStringShort()+ 
                        " bcheckx "+tangent.getX() + c.getCenter().getX() +
                        "checkbounds "+
                        l.checkBounds(tangent.getX() + c.getCenter().getX()) );
            }

            //move circle back to the right center
            //check if contained
            if (l.checkBounds(tangent.getX() + c.getCenter().getX())) {
                return true;
            }
        } else {
            //two points

            //lets do SQRT once
            discriminate = Math.sqrt(discriminate);

            //lets do point one
            Point sec1 = new Point(
                    (D * dy + Math.signum(dy) * dx * discriminate) / drSQR,
                    (-D * dx + Math.abs(dy) * discriminate) / drSQR);

            if (SASLib.Header._DEBUG) {
                System.out.println("sec " + sec1.toStringShort()+ 
                        " bcheckx "+sec1.getX() + c.getCenter().getX() +
                        "checkbounds "+
                        l.checkBounds(sec1.getX() + c.getCenter().getX()) );
            }

            //move circle back to the right center
            //check if contained
            if (l.checkBounds(sec1.getX() + c.getCenter().getX())) {
                return true;
            }

            //lets calc the second sec point
            sec1 = new Point(
                    (D * dy - Math.signum(dy) * dx * discriminate) / drSQR,
                    (-D * dx - Math.abs(dy) * discriminate) / drSQR);


            if (SASLib.Header._DEBUG) {
                System.out.println("sec " + sec1.toStringShort()+ 
                        " bcheckx "+sec1.getX() + c.getCenter().getX() +
                        "checkbounds "+
                        l.checkBounds(sec1.getX() + c.getCenter().getX()) );
            }
            
            //move circle back to the right center
            //check if contained
            if (l.checkBounds(sec1.getX() + c.getCenter().getX())) {
                return true;
            }
        }

        //no colision
        return false;
    }

    /**
     * This method checks if a line is within or crosses a Circle, this is 
     * the faster implemetation.
     * <br>
     * Has a few places where small slivers of circles will produce false
     * negatives, though they are very rare.
     * @param c Circle to check the line with
     * @param l the Line to check if contained
     * @return boolean true if line is within the Circle
     */
    public synchronized static boolean checkCollision(Circle c, Line l) {
        //checking points
        Point pnt1 = l.getPnt1();
        Point pnt2 = l.getPnt2();
        Point pntMiddle;

        //distances and the radius declaration
        double dsqrd1, dsqrd2, dsqrd, rsqrd;

        //how many times to do the binary search through line for a colision point.
        int iters = (int) (pnt1.distanceSq(pnt2) + 1);

        //radius squared
        rsqrd = c.getRadius() * c.getRadius();

        dsqrd1 = c.getCenter().distanceSq(pnt1);
        dsqrd2 = c.getCenter().distanceSq(pnt2);

        //check fast fails
        //end points in circle
        if (SASLib.Header._DEBUG) {
            System.out.println("Circle Line collision debug... Rad SQRD: " +
                    rsqrd + " dists " + pnt1.toStringShort() + dsqrd1 + " " +
                    pnt2.toStringShort() + dsqrd2);
        }
        if (rsqrd >= dsqrd1 || rsqrd >= dsqrd2) {
            return true; //an endpoint is in circle
        }


        //well lets calc middle
        iters = Math.abs(pnt1.getBigX() - pnt2.getBigX());
        do {
            //actual middle point calculation
            pntMiddle = new Point((pnt1.getX() + pnt2.getX()) / 2.0,
                    (pnt1.getY() + pnt2.getY()) / 2.0);

            //calculate distance from circle's center
            dsqrd = c.getCenter().distanceSq(pntMiddle);


            if (SASLib.Header._DEBUG) {
                System.out.println("Iter Dist Point" + iters + " " + dsqrd + " " + pntMiddle.toStringShort());
            }

            //check if point is within circle
            if (rsqrd >= dsqrd) {
                return true;
            }

            //well hell, lets see what the midpoint is now (left or right end)
            if (dsqrd1 >= dsqrd2) {
                //left is farther
                //switch left end to middle
                pnt1 = pntMiddle;
            } else {
                //right is farther
                //switch right end to middle
                pnt2 = pntMiddle;
            }

            //decrease iterations
            iters--;
        } while (iters > 0);

        return false;
    }
    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc=" Line with Line ">  
    /**
     * This method checks if a line is within or crosses a triangle
     * @return boolean true if line is within the triangle
     */
    public synchronized static boolean checkCollision(Line l1, Line l2) {
        double y1, y2, x1, x2, m1, m2;

        //Line One properties
        x1 = l1.getPnt1().getX();
        y1 = l1.getPnt1().getY();
        try {
            m1 = l1.getSlope();
        } catch (ArithmeticException arithmeticException) {
            m1 = Double.MAX_VALUE;
        }

        //Line Two Properties
        x2 = l2.getPnt1().getX();
        y2 = l2.getPnt1().getY();
        try {
            m2 = l2.getSlope();
        } catch (ArithmeticException arithmeticException) {
            m2 = Double.MAX_VALUE;
        }


        //check parallel lines
        if (m1 - m2 == 0) {
            //if b is same...
            //then this is the same line, now check for overlap in domain
            if (y1 - m1 * x1 == y2 - m2 * x2 && l1.checkBounds(y2) && l2.checkBounds(y1)) {
                return true;
            } else {
                //otherwise it time to kill this sucker.
                return false;
            }
        }

        //calc intersection x
        double x = (y2 - y1 + m1 * x1 - m2 * x2) / (m1 - m2);

        if (SASLib.Header._DEBUG) {
            System.out.println("Values : y1 " + y1 + ", y2 " + y2 + ", x1 " + x1 + ", x2 " +
                    x2 + ", m1 " + m1 + ", m2 " + m2);
            System.out.println("The X intersevtion value is " + x + " l1 y " +
                    l1.evatulate(x) + " l2 y " + l2.evatulate(x));
        }

        if (l1.checkBounds(x) && l2.checkBounds(x)) {
            return true;
        }

        return false;
    }
    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc=" Line with Triangle ">  
    /**
     * This method checks if a line is within or crosses a triangle
     * @param t Triangle to check the line with
     * @param l the Line to check if contained
     * @return boolean true if line is within the triangle
     */
    public synchronized static boolean checkCollision(Triangle t, Line l) {
        if (SASLib.Header._DEBUG) {
            System.out.println(t.hashCode() + " checking line collisons with " + l.hashCode());
        }

        //
        //pass case
        //
        //check for easyout, a endpoint is contained
        if (t.contains(l.pnt1) || t.contains(l.pnt2)) {
            //SHORT CIRCUT true
            return true;
        }

        //
        // easy fail case
        //
        //Rectangle. rectangle intersection

        Rectangle boundline = l.getBounds();
        if (!t.bound.intersects(boundline)) {
            return false;
        }



        //
        // recursive step.
        //
        //if large enough difference divide in half and recursive call
        double delta = l.pnt2.x - l.pnt1.x;
        //System.out.println(Math.abs(delta) + " > " + Math.abs(_mindif));
        if (Math.abs(delta) > Math.abs(Triangle._mindif)) {
            //Trinary Break Apart
            delta /= 3.0;
            Point p1 = new Point(l.pnt1.x + delta, l.evatulate(l.pnt1.x + delta));
            Point p2 = new Point(l.pnt1.x + delta * 2.0, l.evatulate(l.pnt1.x + delta * 2.0));

            return (checkCollision(t, new Line(l.pnt1, p1))) ||
                    (checkCollision(t, new Line(p1, p2))) ||
                    (checkCollision(t, new Line(p2, l.pnt2)));
        }

        //to small so this segment must not be in triangle
        return false;
    }
    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc=" Quadrilateral with Quadrilateral ">  
    /**
     * This method checks if a line is within or crosses a Quad
     * @param q1 Quadrilateral one
     * @param q2 Quadrilateral two
     * @return boolean true if intersection.
     */
    public synchronized static boolean checkCollision(Quadrilateral q1, Quadrilateral q2) {
        //check bounding circle and q2 center (as they are not moving we can use this
        Circle c1 = new Circle(q1.getCenter(), q1.getLongestDist() + q2.getLongestDist());

        //fast fail
        if (!contains(c1, q2.getCenter())) {
            return false;
        }

        //they are close enough for at least one edge to be touching.
        //lets check line colisions
        Point[] p1 = q1.getPoints();
        Point[] p2 = q2.getPoints();
        //o(n^2) checking
        for (int i = 0; i < p1.length; i++) {
            for (int k = 0; k < p2.length; k++) {
                //short circut sucess 
                //if ont line crosses then we have at least one point inside the other,
                if (checkCollision(new Line(p1[i], p1[(i + 1) % p1.length]),
                        new Line(p2[k], p2[(k + 1) % p1.length]))) {
                    return true;
                }
            }
        }

        //well hell we're here and there is no line corssing what if 
        //one quad is withing the other
        //
        //split triangles and check
        //
        //we have a method that does this, contains(QUAD, POINT) but we will 
        // _not_ be calling this as this is mildly faster

        //QUAD 1
        boolean check = true;

        //check for center and an edge being in quad
        for (int i = 0; i < p1.length; i++) {
            Line l = new Line(p1[i], p1[(i + 1) % p1.length]);
            check = Math.signum(l.compare(q1.getCenter(), true)) ==
                    Math.signum(l.compare(p2[0], true)) && check;
        }

        //point contained
        if (check) {
            return true;
        }

        //again for quad 2
        check = true;

        //check for center and an edge being in quad
        for (int i = 0; i < p2.length; i++) {
            Line l = new Line(p2[i], p2[(i + 1) % p2.length]);
            check = Math.signum(l.compare(q2.getCenter(), true)) ==
                    Math.signum(l.compare(p1[0], true)) && check;
        }

        //point contained
        return check;
    }
    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc=" Quadrilateral with Triangle ">  
    /**
     * This method checks if a line is within or crosses a Quad
     * @param q Quadrilateral 
     * @param t Triangle
     * @return boolean true if intersection.
     */
    public synchronized static boolean checkCollision(Quadrilateral q, Triangle t) {
        //check bounding box
        if (!q.getBounds().intersects(t.getBounds())) {
            return false;
        }

        //they are close enough for at least one edge to be touching.
        //lets check line colisions
        Point[] p1 = q.getPoints();
        Line[] l1 = t.getLines();
        //o(n^2) checking, not to shaby
        for (int i = 0; i < p1.length; i++) {
            for (int k = 0; k < l1.length; k++) {
                //short circut sucess 
                //if ont line crosses then we have at least one point inside the other,
                if (checkCollision(new Line(p1[i], p1[(i + 1) % p1.length]), l1[k])) {
                    return true;
                }
            }
        }

        //well hell we're here and there is no line corssing what if 
        //one quad is withing the other
        //
        //split triangles and check
        //

        //point contained
        if (contains(t, p1[0])) {
            //short out true
            return true;
        }

        //QUAD check for Triangle vertex
        //this is last line so we can use return.
        return contains(q, l1[0].getPnt1());
    }
    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc=" Triangle with Triangle ">  
    /**
     * This method checks if a line is within or crosses a triangle
     * @param t1 Triangle to check if contained
     * @param t2 Triangle to check if contained
     * @return boolean true if line is within the triangle
     */
    public synchronized static boolean checkCollision(Triangle t1, Triangle t2) {
        if (SASLib.Header._DEBUG) {
            System.out.println(t1.hashCode() + " is Checking Collision with " + t2.hashCode());
        }
        //short circut false if same
        if (t1 == t2) {
            if (SASLib.Header._DEBUG) {
                System.out.println("Self Collision");
            }
            return false;
        }

        //
        //check for fastfail ... 
        // Rectangle intersection...
        //
        if (!t1.bound.intersects(t2.bound) || !t1.fresh || !t2.fresh) {
            return false;
        }

        //check all the lines of the triangle but if even one passes then we 
        //have an intersection and we can stop
        for (int i = 0; i < t1.lines.length; i++) {

            if (checkCollision(t2, t1.lines[i])) {
                //one line intersected, short circut true.
                if (SASLib.Header._DEBUG) {
                    System.out.println("Line Intersected, SC true");
                }
                return true;
            }
        }

        //all failed
        return false;
    }

    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc=" Shapes and a Point ">  
    /**
     * This method checks if a point is within the triangle
     * @param t Triangle to check if point is within.
     * @param p Point to check if contained
     * @return boolean true if point is within the triangle
     */
    public synchronized static boolean contains(Triangle t, Point p) {
        //if x or y out of bounds
        if (!t.bound.contains(p)) {
            //short circut false
            return false;
        }

        // calc xmin, xmax
        double tymin = Double.MAX_VALUE;
        double tymax = Double.MIN_VALUE;

        for (int i = 0; i < t.lines.length; i++) {
            //Evaluate y values for p.x and min max them to ymin and ymax
            try {
                double y = t.lines[i].evatulate(p.x);
                tymin = Math.min(y, tymin);
                tymax = Math.max(y, tymax);

            } catch (ArithmeticException arithmeticException) {
                //do nothin
                if (SASLib.Header._DEBUG) {
                    System.out.println("Exception on contains, " + arithmeticException.getMessage());
                }
            }

        }

        //check if between y values for x
        if (p.y < tymin || p.y > tymax) {
            //short circut false
            return false;
        }

        return true;
    }

    /**
     * This method checks if a point is within the circle
     * @param c Circle to check if point is within.
     * @param p Point to check if contained
     * @return boolean true if point is within the circle
     */
    public synchronized static boolean contains(Circle c, Point p) {
        //get d^2 between center and p
        double dsqrd = c.getCenter().distanceSq(p);

        //check if d^2 is GRTR THAN radius^2
        if (dsqrd > c.getRadius() * c.getRadius()) {
            return false;
        }
        return true;
    }

    /**
     * This method checks if a point is on a line
     * @param l Line to check if point is within.
     * @param p Point to check if contained
     * @return boolean true if point is on the line
     */
    public synchronized static boolean contains(Line l, Point p) {

        if (l.evatulate(p.getX()) == p.getY()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a point is within a Quadrilateral.
     * @param q Quadrilateral to check if the point is in
     * @param p The point
     * @return true iff the point is contained in the quad
     */
    public synchronized static boolean contains(Quadrilateral q, Point p) {
        Point[] p1 = q.getPoints();
        boolean check = true;

        //check for center and an edge being in quad
        for (int i = 0; i < p1.length; i++) {
            Line l = new Line(p1[i], p1[(i + 1) % p1.length]);
            check = check && Math.signum(l.compare(q.getCenter(), true)) == Math.signum(l.compare(p, true));
        }

        //returns true iff the point is contained in the quad
        return check;
    }
    // </editor-fold>  
}
