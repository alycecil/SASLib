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
    Vector
Purpose:
    Define a simple class to represent all vector
Coders :
    Wil Cecil
Created:
    Feb 4th 2008
Change Log: 
 */
/*
 * Code Addapted From VB Source-ish and wiki on 
 * http://gpwiki.org/index.php/VB:Building_A_Simple_Physics_Engine_Part_1
 */

package SASLib.Geom;

/**
 * This is a Vector Class it holds an x and y 
 * @author Wil Cecil
 */
public class Vector implements Cloneable{

    double X, Y;
    
    /**
     * Creates Vector with X,Y
     * @param X
     * @param Y
     */
    public Vector(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }
    
    /**
     * Create vector from point X,Y cords 
     * @param pnt
     */
    public Vector(Point pnt) {
        this.X = pnt.x;
        this.Y = pnt.y;
    }
    
    /**
     * 0,0 Vector
     */
    public Vector() {
        X=0;
        Y=0;
    }
    
    
    
    /**
     * Scallar Magnitude of Vector
     * @return double that is the scallar represnetation of the Vector
     */
    public double Magnitude() {
        return Math.pow(X * X + Y * Y,2);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * calls clone from outside this package.
     * 
     * @return clone of this object
     */
    public Vector copy(){
        try {
            return (Vector) clone();
        } catch (CloneNotSupportedException ex) {   
        }
        //return blank vector.
        return null;
    }

    /**
     * Does the dot product between this and Vector B
     * @param B
     * @return the dot prodict as a double
     */
    public double dot(Vector B) {
        return X * B.X + Y * B.Y;
    }
    
    /**
     * Calculates the Scalar Distance beteen two vectors
     * @param B
     * @return double of the vector distance
     */
    public double VectorDistance(Vector B){
        return Math.pow((X - B.X) * (X - B.X) + (Y - B.Y) * (Y - B.Y),2.0);
    }

    /**
     * Difference of two vectors
     * @param B
     * @return calling object.
     */
    public Vector minus(Vector B) {
        X-=B.X;
        Y-=B.Y;
        return this;
    }
    
    /**
     * subtracrs (x,y) by (s,s)
     * @param scallar
     * @return calling vector
     */
    public Vector minus(Double scallar) {
        X-=scallar;
        Y-=scallar;
        return this;
    }
    
    /**
     * Addition of two vectors
     * @param B
     * @return this
     */
    public Vector plus(Vector B) {
        X+=B.X;
        Y+=B.Y;
        return this;
    }
    
    /**
     * adds (x,y) by (s,s)
     * @param scallar
     * @return calling vector
     */
    public Vector plus(Double scallar) {
        X+=scallar;
        Y+=scallar;
        return this;
    }

    /**
     * Normalizes vector
     * @return this vector after normalized
     */
    public Vector normalize() {
        double mag = Magnitude();
        
        if(mag != 0){
            X/=mag;
            Y/=mag;
        }
        
        return this;
    }

    /**
     * multiplies (x,y) by (s,s)
     * @param scallar
     * @return calling vector
     */
    public Vector times(double scallar) {
        X*=scallar;
        Y*=scallar;
        return this;
    }

    /**
     * Sets X Value
     * @param X
     */
    public void setX(double X) {
        this.X = X;
    }

    /**
     * Sets Y Value
     * @param Y
     */
    public void setY(double Y) {
        this.Y = Y;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    /**
     * returns this vector as a point.
     * @return Point    this Vectors Point equivilent 
     */
    public Point asPoint(){
        return new Point(X,Y);
    }
}
