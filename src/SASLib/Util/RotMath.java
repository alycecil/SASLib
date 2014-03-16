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
    RotMath
Purpose:
    Handle all sorts of common rotational math as well as hold a precalulated 
    table of sin and cos
Coders :
    Wil Cecil
Created:
    March 30, 2007, 1:45 PM
Change Log: 
 */



package SASLib.Util;

import SASLib.Geom.*;

/**
 * Handle all sorts of common rotational math as well as hold a precalulated 
 * table of sin and cos
 * 
 * @author Wil Cecil
 */
public class RotMath {

    /** Creates a new instance of RotMath */
    public RotMath() {
        LoadTables();
    }
    
    /**
     * 
     */
    public static double[] tSine;
    
    /**
     * 
     */
    public static double[] tCoSine;

    /**
     * Loads the tables... this is good, infact so good if you forget to do it 
     * we will do it if You forget to.
     */
    public static void LoadTables() {
        tSine = new double[3600];
        tCoSine = new double[3600];
        for (double i = 0.0; i < 3600.0; i++) {
            tSine[(int) i] = Math.sin((i / 10.0) * Math.PI / 180.0);
            tCoSine[(int) i] = Math.cos((i / 10.0) * Math.PI / 180.0);
        }
    }

    /**
     * Rotates a point around a non-origin center
     * @param pPoint
     * @param pOrigin
     * @param Degrees
     * @return Point    rotated about the point pOrigin
     */
    public static Point RotatePoint(Point pPoint, Point pOrigin, double Degrees) {
        Point RotatePoint = new Point(
                CoSine(Degrees) * (pPoint.getX() - pOrigin.getX()) - 
                Sine(Degrees) * (pPoint.getY() - pOrigin.getY()), 
                Sine(Degrees) * (pPoint.getX() - pOrigin.getX()) + 
                CoSine(Degrees) * (pPoint.getY() - pOrigin.getY()));
        
        RotatePoint=RotatePoint.translate(pOrigin);
        return RotatePoint;
    }

    /**
     * Rotates a point around the origin
     * @param pPoint
     * @param Degrees
     * @return Point    rotated about the origin
     */
    public static Point RotatePoint(Point pPoint, double Degrees) {
        Point RotatePoint = new Point(
                CoSine(Degrees) * (pPoint.getX() ) - 
                Sine(Degrees) * (pPoint.getY() ), 
                Sine(Degrees) * (pPoint.getX() ) + 
                CoSine(Degrees) * (pPoint.getY() )
                );
        return RotatePoint;
    }

    
    /**
     *  Calc Sine From Lookup Table
     * @param Angle in degrees
     * @return Sine of angle
     */
    public static double Sine(double Angle) {
        int A = (int) Math.abs((Angle*10)%3600);//Get to Right size to use lookup table
        try {
            return tSine[A];
        } catch (Exception e) {
            LoadTables();
            return tSine[A];
        }
    }

    /**
     *  Calc CoSine From Lookup Table
     * @param Angle in degrees
     * @return double value of cos of angle
     */
    public static double CoSine(double Angle) {
        int A = (int) Math.abs((Angle*10)%3600);//Get to Right size to use lookup table
        try {

            return tCoSine[A];
        } catch (Exception e) {
            LoadTables();
            return tCoSine[A];
        }
    }

    /**
     * check if a point is in side a rectangle between (x0, y0) and (x1, y1).
     * Also the 0 pair are are before the 1 pair
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param p
     * @return boolean  
     */
    public static boolean pointInside(double x0, double y0, double x1, double y1, Point p) {
        if (p.getX() > x0 && p.getY() > y0 && p.getX() < x1 && p.getY() < y1) {
            return true;
        }
        return false;
    }
    
    /**
     * Drops all sin cos points as a grid with rows of size width
     */
    public static void dumpTables(int width){
        String cos, sin;
        for(int i = 0; i < tCoSine.length; i++){
            //displays all values within the table as [x,y] st x,y have lengths of 6
            cos = "      ".concat(Double.toString(Math.round(tCoSine[i]*100000.0)/100000.0));
            cos = cos.substring(cos.length()-7);
            sin="        ".concat(Double.toString(Math.round(tSine[i]*100000.0)/100000.0));
            sin = sin.substring(sin.length()-7);
            
            System.out.print("["+cos+", "+sin+"] ");
            
            //line feed at width.
            if(i%width==width-1){
                System.out.println();
            }
        }
            
    }
}