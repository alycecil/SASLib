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
    AStarSearch
Purpose:
    The AStarSearch class, along with the AStarNode class,
    implements a generic A* search algorithm. The AStarNode
    class should be subclassed to provide searching capability
Coders :
    Wil Cecil
Created:
    March 30, 2007
Change Log: 
 */

package SASLib.PathFinding;

import java.util.*;

/**
 * The AStarSearch class, along with the AStarNode class,
 * implements a generic A* search algorithm. The AStarNode
 * class should be subclassed to provide searching capability.
 * 
 * @author Wil Cecil
 */
public class AStarSearch {
    public AStarSearch(){
    }
    /**
     * A simple priority list, also called a priority queue.
     * Objects in the list are ordered by their priority,
     * determined by the object's Comparable interface.
     * The highest priority item is first in the list.
     */
    public class PriorityList extends LinkedList {
        
        public synchronized void add(Comparable object) {
            for (int i=0; i<size(); i++) {
                if (object.compareTo(get(i)) <= 0) {
                    add(i, object);
                    return;
                }
            }
            
            addLast(object);
        }
    }
    
    
    /**
     * Construct the path, not including the start node.
     */
    protected synchronized List constructPath(AStarNode node) {
        LinkedList path = new LinkedList();
        while (node.pathParent != null) {
            path.addFirst(node);
            node = node.pathParent;
        }
        return path;
    }
    
    
    /**
     * Find the path from the start node to the end node. A list
     * of AStarNodes is returned, or null if the path is not
     * found.
     */
    public synchronized List findPath(AStarNode startNode, AStarNode goalNode) {
        
        PriorityList openList = new PriorityList();
        LinkedList closedList = new LinkedList();
        
        startNode.costFromStart = 0;
        startNode.estimatedCostToGoal =
                startNode.getEstimatedCost(goalNode);
        startNode.pathParent = null;
        openList.add(startNode);
        
        while (!openList.isEmpty() ){
            AStarNode node = (AStarNode)openList.removeFirst();
            if ( node.equals( goalNode ) ) {
                //System.out.println(node.getX() + "\n" +
                //        node.getY() + "\n" +
                //        node.getCost( goalNode ) + "\n" +
                //        node.pathParent);
                // construct the path from start to goal
                return constructPath(node);
            }
            
            List neighbors = node.getNeighbors();
            for (int i=0; i<neighbors.size(); i++) {
                AStarNode neighborNode =
                        (AStarNode)neighbors.get(i);
                boolean isOpen = openList.contains(neighborNode);
                boolean isClosed =
                        closedList.contains(neighborNode);
                float costFromStart = node.costFromStart +
                        node.getCost(neighborNode);
                
                // check if the neighbor node has not been
                // traversed or if a shorter path to this
                // neighbor node is found.
                if ((!isOpen && !isClosed) ||
                        costFromStart < neighborNode.costFromStart) {
                    neighborNode.pathParent = node;
                    neighborNode.costFromStart = costFromStart;
                    neighborNode.estimatedCostToGoal =
                            neighborNode.getEstimatedCost(goalNode);
                    if (isClosed) {
                        closedList.remove(neighborNode);
                    }
                    if (!isOpen) {
                        openList.add(neighborNode);
                    }
                }
                closedList.add(node);
            }
        }
        // no path found
        return null;
    }
}