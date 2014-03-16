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

import java.awt.Point;
import java.util.LinkedList;

/**
 * This is a bucket for <code>Entity</code> that are near by to each other.
 * 
 * @author Wil Cecil
 */
class Location {
    /**
     * this is the physical bucket
     */
    LinkedList<Entity> myEntities;
    
    /**
     * this is the Grid Position
     */
    java.awt.Point loc;
    
    /**
     * This is the default constructor
     * @param gridloc this is used to define the location of this grid.
     */
    public Location(java.awt.Point gridloc) {
        myEntities = new LinkedList<Entity>();
        loc = gridloc;
    }

    /**
     * @return returns a linkedlist of all entities in this location
     */
    public LinkedList<Entity> getEntities() {
        return myEntities;
    }

    /**
     * Add an entity to this location
     * @param e
     */
    public boolean addEntity(Entity e) {
        return myEntities.add(e);
    }

    /**
     * Removes an entiry from the location
     * @param e entity to remove
     * @return if we where able to remove the entity
     */
    public boolean removeEntity(Entity e) {
        return myEntities.remove(e);
    }
    
    
    /**
     * @return this locations grid location
     */
    public Point getLoc() {
        return loc;
    }
    
    /**
     * This returns an int with the number of objects in location
     * @return int number of entities in this location
     */
    public int getSize(){
        return myEntities.size();
    }
    
}
