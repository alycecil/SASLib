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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import javax.media.opengl.GL;

/**
 * This is a Simple Entity and World state manager that can render everthing 
 * in it with a OpenGL object.
 * 
 * @author Wil Cecil
 */
public class ObjectManager {

    //LinkedList<Entity> astroids;
    
    /**
     * Grid size in _y_ direction
     */
    double gridY = 100;
    
    /**
     * grid size in _x_ direction
     */
    double gridX = 100;
    
    /**
     * this is a HashMap of all entities
     */
    HashMap<java.awt.Point, Location> astroids;

    /**
     * Defualt contructor, no parameters. 
     * Simply creates the <code>HashMap</code> of all <code>Entity</code>.
     */
    public ObjectManager() {
        astroids = new HashMap<java.awt.Point, Location>();
    }

    /**
     * Calculates the grid location for this object.
     * @param x
     * @param y
     * @return java.awt.Point with x,y representing a grid location
     */
    private java.awt.Point calcGrid(int x, int y) {
        java.awt.Point grid = new java.awt.Point();
        grid.x = (int) (x / gridX + 0.5);
        grid.y = (int) (y / gridY + 0.5);
        return grid;
    }

    /**
     * Caclulates all the grid location that this object occupies.
     * @param e
     * @return java.util.HashSet<java.awt.Point> containing all the grid locations as java.awt.Point
     */
    private java.util.HashSet<java.awt.Point> getGrids(Entity e) {
        Rectangle rect = e.getBoundingBox();
        java.util.HashSet<java.awt.Point> grids = new HashSet<java.awt.Point>();


        gridRectCalc(rect, grids);


        return grids;
    }

    /**
     * Adds the grid locs of rectangle endpoint to a hashset
     * @param rect
     * @param grids
     */
    private void gridRectCalc(Rectangle rect, HashSet<java.awt.Point> grids) {
        //add all four points of rectangle calculated as grid locs to the set
        grids.add(calcGrid(rect.x, rect.y));
        grids.add(calcGrid(rect.x + rect.width, rect.y));
        grids.add(calcGrid(rect.x, rect.y + rect.height));
        grids.add(calcGrid(rect.x + rect.width, rect.y + rect.height));
    }

    //
    // this could be made smarter to only remove what 
    // no longer is needed and add to new ones only.
    //
    /**
     * This updates a Entity with the grid locations of the object for collision detection
     * @param e
     * @param oldBounds
     * @return boolean true iff was able to remove entity from all grids it occupied
     */
    public boolean updateEntity(Entity e, Rectangle oldBounds) {
        //look through the old bounding boxes and remove from the locations


        Iterator<java.awt.Point> iter2;
        java.util.HashSet<java.awt.Point> grids = new HashSet<java.awt.Point>();
        boolean value = true;
        Location curr;

        //CALC GRIDS
        gridRectCalc(oldBounds, grids);


        //Remove from
        iter2 = grids.iterator();
        while (iter2.hasNext()) {
            curr = astroids.get(iter2.next());
            value = curr.removeEntity(e) && value;

            //if empty drop...
            if (curr.getSize() == 0) {
                astroids.remove(curr.loc);
            }
        }

        //re-add
        add(e);

        return value;
    }

    /**
     * This method adds an Entity to the world.
     * @param loadEntity
     * @return boolean true if able to add to all grids sucsessfully
     */
    public boolean add(Entity loadEntity) {
        boolean added = true;

        //go through all this entities grids
        Iterator<java.awt.Point> iter = getGrids(loadEntity).iterator();
        while (iter.hasNext()) {
            //Add to locations
            java.awt.Point gridloc = iter.next();
            Location loc = astroids.get(gridloc);

            //check for blank Location
            if (loc == null) {
                loc = new Location(gridloc);
            }

            //add entity to location
            added = loc.addEntity(loadEntity) && added;

            //reput location into Map
            astroids.put(gridloc, loc);
        }

        return added;
    }

    /**
     * This method removes an entity from the world
     * @param loadEntity
     * @return boolean true iff was able to remove entity from all grids it occupied
     */
    public boolean remove(Entity loadEntity) {
        //getGrids(loadEntity);
        boolean removed = true;
        Iterator<java.awt.Point> iter = getGrids(loadEntity).iterator();
        while (iter.hasNext()) {
            java.awt.Point gridloc = iter.next();
            Location loc = astroids.get(gridloc);

            //check for blank Location
            if (loc != null) {
                //remove entity to location
                removed = loc.removeEntity(loadEntity) && removed;
            } else {
                removed = false;
            }
            //reput location into Map
            astroids.put(gridloc, loc);

        }

        return removed;
    }

    /**
     * This method checks collisions with Entity
     * @param e this is a Entity to check for collisions with.
     * @param act 
     * @return boolean if true a collision has happened.
     */
    public boolean checkCollisions(Entity e, boolean act) {
        Iterator<java.awt.Point> grids = getGrids(e).iterator();
        boolean colide = false;
        Entity a2;

        //walk through all of Entitys grids
        while (grids.hasNext()) {
            Iterator<Entity> ents = astroids.get(grids.next()).getEntities().iterator();
            while (ents.hasNext()) {
                a2 = ents.next();
                if (a2.checkCollision(e,true)) {
                    if (act) {
                        e.colide(a2);
                        colide = true;
                    } else {
                        return true;
                    }
                }
            }
        }

        return colide;
    }

    /**
     * This returns a Collection of all the entities within this ObjectManager. 
     * @return Collection<Entity> but this implimentation returns a LinkedList<Entity>
     */
    public Collection<Entity> getEntities() {
        LinkedList<Entity> entities = new LinkedList<Entity>();

        //Get All entities from locations and export all of them.

        //create an iterator of Locations
        Iterator<Location> iter = astroids.values().iterator();

        //loop through all known locations
        while (iter.hasNext()) {
            //create iter of all entites in location 
            Iterator<Entity> iter2 = iter.next().getEntities().iterator();

            //loop through all entities
            while (iter2.hasNext()) {
                //add entities from location to master List
                entities.add(iter2.next());
            }
        }

        //return the list
        return entities;
    }

    /**
     * this activates all active Entitys, through there move() method.
     */
    public void logic() {
        Iterator<Entity> iter = getEntities().iterator();
        while (iter.hasNext()) {
            Entity a = iter.next();

            if (!checkCollisions(a, true)) {
                //keep reference to old box
                Rectangle rect = a.getBoundingBox();
                
                //move them.
                a.move();
                
                //update location
                updateEntity(a, rect);
            }
        }
    }

    /**
     * Renders all asteriods in this object, attempts to texture each one first
     * @param gl
     */
    public void render(GL gl) {
        Iterator<Entity> iter = getEntities().iterator();
        //lock and enable texture
        Entity e;
        while(iter.hasNext()){
            e = iter.next();
            e.texture();
            e.render(gl);
        }
    }
    
    
}
