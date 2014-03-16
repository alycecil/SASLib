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
    Direction
Purpose:
    Provide simple quad directional support
Coders :
    Wil Cecil
Created:
    January 23, 2007, 5:06 PM
Change Log: 
 */

package SASLib.PathFinding;

import SASLib.Geom.Point;

/**
 * Provide simple quad directional support
 * @author Wil Cecil
 */
public abstract class SimpleDirection {

    private static int NO_DIR = -1;
    public static final int North = 0;
    public static final int East = 1;
    public static final int South = 2;
    public static final int West = 3;

    public static int reverse(int direction) {
        if (direction == North) {
            return South;
        } else if (direction == East) {
            return West;
        } else if (direction == South) {
            return North;
        } else if (direction == West) {
            return East;
        }

        return North;
    }

    /**
     * Calculates a point based on an x, y and a direction
     * @param x
     * @param y
     * @param direction
     * @return Point    point moved in direction
     */
    public static Point calculate(int x, int y, int direction) {
        if (direction == North) {
            y++;
        } else if (direction == East) {
            x++;
        } else if (direction == South) {
            y--;
        } else if (direction == West) {
            x--;
        }

        return new Point(x, y);
    }

    /**
     * Calculates a direction of travel to return to origin based on 2 sets of x, y.
     * North, South Checked First.
     * @param x Origin
     * @param y Origin
     * @param x1 new Point
     * @param y1 new Point
     * @return int code of Direction or NO_DIR
     */
    public static int calculate(int x, int y, int x1, int y1) {
        //int dX = Math.abs(x-x1);
        //int dY = Math.abs(y-y1);


        //if(dY<dX){ //Y Dir Closer
        if (y < y1) {
            return North;
        } else if (y > y1) {
            return South;
        }
        //} else{ // X Dir Closer
        if (x < x1) {
            return East;
        } else if (x > x1) {
            return West;
        }
        //}

        return NO_DIR;
    }
}
