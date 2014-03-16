// Copyright (C) 2005 William Bogg Cecil. All rights reserved. Use is
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
    BarGraph
Purpose:
    To draw a bargraph to represent a single array of doubles
Coders :
    Wil Cecil
Created:
    Feb 21st 2008
Change Log: 
 */

package SASLib.Util;

import SASLib.GL.ShapeRenderer;
import java.awt.Rectangle;
import java.util.LinkedList;

import javax.media.opengl.GL2;

/**
 * .................<br>
 * ...Incomplete!...<br>
 * .................<br>
 * 
 * @author Wil Cecil
 */
public abstract class BarGraph implements Graph{
    /**
     * width of individual bars in value size
     */
    double barsize;
    
    /**
     * this is the actual data in 2 dim
     * 1st dim is the bar
     * 2nd dim is the physical data
     */
    LinkedList<Double> [] pdata;
    
    /**
     * this is the physical size of the largest bar.
     */
    int largestBar;
    
    /**
     * linear splitter
     * @param data
     */
    public BarGraph(double[] data){
        double min = Double.MAX_VALUE, max=Double.MIN_VALUE;
        
        //find min max
        for(int i = 0; i < data.length; i++){
            min = Math.min(min, data[i]);
            max = Math.max(max, data[i]);
        }
        
        //splits the data into max(log(data.length),5) sections
        pdata = new LinkedList[(int)(Math.max(5.0, data.length)+1)];
        int index;
        barsize = (max-min)/pdata.length;
        
        for(int i = 0; i < data.length; i++){
            //find where to put element
            index = (int) ((data[i] - min + 1) / barsize + 1);
            
            //see if spot is null
            if(pdata[index]==null){
                pdata[index] = new LinkedList<Double>();
            }
            
            //add to spot
            pdata[index].add(data[i]);
        }
        
        //find the largest bar
        largestBar=Integer.MIN_VALUE;
        for(int i = 0; i < pdata.length; i++){
            if(pdata[i]!=null){
            largestBar = Math.max(largestBar, pdata[i].size());
            }
        }
    }
    
    /**
     * Draws the bargraph
     * @param gl gl object
     * @param x start x
     * @param y start y
     * @param zoom z depth
     */
    public synchronized void render(GL2 gl, double x, double y, double width, double height, double zoom){
        //make width the individual bar width
        width/=pdata.length;
        
        //make height a multiplier by size
        height=(double)largestBar/height;
        for(int i = 0; i < pdata.length; i++){
            if(pdata[i]!=null){
                ShapeRenderer.render(
                        new Rectangle((int)(width*i+1),(int)(height*pdata[i].size()+1)), gl, x, y, zoom);
            }
        }
    }
}
