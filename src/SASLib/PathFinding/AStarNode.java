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
    AStarNode
Purpose:
    The AStarNode class, along with the AStarSearch class,
    implements a generic A* search algorithm. The AStarNode
    class should be subclassed to provide searching capability
Coders :
    Wil Cecil
Created:
    March 30, 2007
Change Log: 
 */

package SASLib.PathFinding;

import java.util.List;

/**
 * The AStarNode class, along with the AStarSearch class,
 * implements a generic A* search algorithm. The AStarNode
 * class should be subclassed to provide searching capability.
 * 
 * @author Wil Cecil
 */
public abstract class AStarNode implements Comparable {
    AStarNode pathParent;
    float costFromStart;
    float estimatedCostToGoal;
    int     x,
            y;

    /**
     * Returns the X cord of this node
     * @return int x cord
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y cord of this node
     * @return int y cord
     */
    public int getY() {
        return y;
    }
    
    public float getCost() {
        return costFromStart + estimatedCostToGoal;
    }
    
    
    public int compareTo(Object other) {
        float thisValue = this.getCost();
        float otherValue = ((AStarNode)other).getCost();
        
        float v = thisValue - otherValue;
        return (v>0)?1:(v<0)?-1:0; // sign function
    }
    
    
    /**
    Gets the cost between this node and the specified
    adjacent (AKA "neighbor" or "child") node.
     * @param node
     * @return float
     */
    public abstract float getCost(AStarNode node);
    
    
    /**
    Gets the estimated cost between this node and the
    specified node. The estimated cost should never exceed
    the true cost. The better the estimate, the more
    effecient the search.
     * @param node
     * @return float
     */
    public abstract float getEstimatedCost(AStarNode node);
    
    
    /**
    Gets the children (AKA "neighbors" or "adjacent nodes")
    of this node.
     * @return List
     */
    public abstract List getNeighbors();

    /**
     * Checks if this node is equvilent to another
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if( obj.getClass().isAssignableFrom(this.getClass()) ){
            AStarNode sn = (AStarNode)obj;
            return (sn.getX()==getX()&&sn.getY()==getY());
        }else{
            return false;
        }
        
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Float.floatToIntBits(this.costFromStart);
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
        return hash;
    }
    


}