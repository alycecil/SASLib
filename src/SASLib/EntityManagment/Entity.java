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
package SASLib.EntityManagment;

import java.awt.Rectangle;
import javax.media.opengl.GL;

/**
 *This is an Entity or object within a world / ObjectManager.
 * 
 * @author Wil Cecil
 */
public interface Entity {

    /**
     * Checks for a collison between two entities, try using 
     * <code>SASlib.Geom.*</code> for the shape of the object and 
     * <code>SASlib.Geom.CollisionHandler</code> for colision detection.
     * 
     * @param a2 the entity to check for collisions with
     * @param moving if this object is moving use multiple positional test
     * @return boolean, true if most likely a collision
     */
    public boolean checkCollision(Entity a2, boolean moving);

    /**
     * Does collison math. 
     * <br>Ie Bouncing, damaging, ect
     * @param a2
     */
    public void colide(Entity a2);

    /**
     * This creates a rectangle that holds the entire object.
     * <code>SASlib.Geom.*</code> for the shape of the object then the method 
     * <code>getBounds()</code> for best results.
     * 
     * @return Rectangle
     */
    public Rectangle getBoundingBox();

    /**
     * Moves this point to the next time step
     */
    public void move();

    /**
     * Genders this object to a opengl
     * @param gl
     */
    public void render(GL gl);
    
    /**
     * Checks if the object is actually textured the textute
     */
     public boolean isTextured();
    
    /**
     * Activates and enables the textute
     */
     public void texture();

}
