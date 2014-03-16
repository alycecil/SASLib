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
    Shape
Purpose:
    Define a simple interface to represent all shapes
Coders :
    Wil Cecil
Created:
    Feb 6th 2008
Change Log: 
 */

package SASLib.Geom;

import java.awt.Rectangle;

/**
 * Define a simple interface to represent all shapes
 * 
 * @author Wil Cecil
 */
public interface Shape extends Cloneable{
    
    /**
     * Creates a rectangle that contains the entire object.
     * @return the bounding rectangle.
     */
    public Rectangle getBounds();
}
