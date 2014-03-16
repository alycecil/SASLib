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
    Grid2d
Purpose:
    Hold data of any two dim numeric grid and be able to do edge detection 
    upon that data
Coders :
    Wil Cecil
Created:
    September 27, 2007, 11:41 AM
Change Log: 
 */

package SASLib.Util;

import SASLib.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Hold data of any two dim numeric grid and be able to do edge detection 
 * upon that data.
 * 
 * @author Wil Cecil
 */
public class Grid2d {
    double[][] world;
    int height;
    int width;
    double max;
    
    /** Creates a new instance of World */
    public Grid2d(int h, int w) {
        height = h;
        width = w;
        world = new double[height][width];
        max=0.0;
    }
    
    /**
     * Set Value in GridBlackAndWhite
     */
    public void setXY(int x, int y, double value){
        if(value>max){
            max = value;
        }
        
        world[y][x] = value;
    }
    
    /**
     *Get Value from GridBlackAndWhite
     */
    public double getXY(int x, int y){
        double value = 0.0;
        value = world[y][x];
        return value;
    }
    
    public int getHeight() {
        return Math.min(world.length,height);
    }
    
    public int getWidth() {
        return Math.min(width,world[0].length);
    }
    
    /**
     *Finds the Difference Quotent for all points.
     */
    public Grid2d derivitive(boolean vertical){
        //Dif Quotent GridBlackAndWhite
        Grid2d dg = new Grid2d(height,width);
        
        //O(n^2)
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(vertical){
                    if(i==0){
                        //nothing above, dg/dy will be just diff to next
                        double above = ( (world[i+1][j]-world[i][j]))/2.0;
                        dg.setXY(j,i, above );
                        
                    }else if(i==height-1){
                        //nothing bellow, dg/dy will be just diff to previous
                        double bellow = ( (world[i-1][j]-world[i][j]))/2.0;
                        dg.setXY(j,i, bellow );
                        
                    }else{
                        //yay we're somewhre in the middle
                        double above = ( (world[i+1][j]-world[i][j]))/2.0;
                        double bellow = ( (world[i-1][j]-world[i][j]))/2.0;
                        if(Header._DEBUG) System.out.print(above + "|" + bellow + " ");
                        dg.setXY(j,i, (above + bellow) /2.0 );
                    }
                }else{
                    if(j==0){
                        //nothing before, dg/dy will be just diff to next
                        double above = ( (world[i][j+1]-world[i][j]))/2.0;
                        dg.setXY(j,i, above );
                        
                    }else if(j==width-1){
                        //nothing after, dg/dy will be just diff to previous\\
                        double bellow = ( (world[i][j-1]-world[i][j]))/2.0;
                        dg.setXY(j,i, bellow );
                        
                    }else{
                        //yay we're somewhre in the middle
                        double above = ( (world[i][j+1]-world[i][j]))/2.0;
                        double bellow = ( (world[i][j-1]-world[i][j]))/2.0;
                        if(Header._DEBUG) System.out.print( bellow+ "|" + above + "  ");
                        dg.setXY(j,i, (above + bellow) /2.0 );
                    }
                }
                
                //ABS
                dg.setXY(j,i, Math.abs( dg.getXY(j,i) ) );
            }
            if(Header._DEBUG) System.out.println();
        }
        
        return dg;
    }
    
    //XX or '  ' print from a MAX/2 threashhold
    public void print2d(){
        print2d(max/2.0);
    }
    
    //X or ' ' print from a parameter threashhold
    public void print2d(double threash){
        
        for(int y = 0; y < getHeight(); y++){
            for(int x = 0; x < getWidth(); x++){
                if(getXY(x,y)>threash){
                    System.out.print("XX");
                }else
                    System.out.print("  ");
            }
            System.out.println();
        }
    }
    
    /**
     * Read an img file into this grid.
     */
    public static Grid2d convert(BufferedImage img){
        //
        //
        Grid2d g = new Grid2d(img.getHeight(),img.getWidth());
        WritableRaster r = img.getRaster();
        double[] pixel = new double[5];
        for(int y = 0; y < g.getHeight(); y++){
            for(int x = 0; x < g.getWidth(); x++){
                
                try{
                    r.getPixel(x,y,pixel);
                }catch(Exception e){
                    System.out.println("Pixel Error on ("+x+", "+y+")" +
                            "\n"+e.toString());
                    break;
                }
                
                //.33 each
                //double avgValue=(pixel[0]+pixel[1]+pixel[2])/3.0;
                
                //0.3*R + 0.59*G + 0.11*B
                double avgValue=(pixel[0]*.3+pixel[1]*.59+pixel[2]*.11);
                pixel[0]=avgValue;
                pixel[1]=avgValue;
                pixel[2]=avgValue;
                
                r.setPixel(x,y,pixel);
                
                //Simple Avg RGB
                g.setXY(x,y,avgValue);
                
                if(Header._DEBUG) System.out.print(g.getXY(x,y)+" ");
            }
            if(Header._DEBUG) System.out.println();
        }
        
        
        return g;
    }
    
    public Grid2d max(Grid2d g2){
        Grid2d ret = new Grid2d(
                Math.min(getHeight(),g2.getHeight()),
                Math.min(getWidth(),g2.getWidth()));
        
        for(int y = 0; y < ret.getHeight(); y++){
            for(int x = 0; x < ret.getWidth(); x++){
                try{
                    double ldmax = Math.max(getXY(x,y),g2.getXY(x,y));
                    ret.setXY(x,y,ldmax);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
    
    public Grid2d avg(Grid2d g2){
        Grid2d ret = new Grid2d(
                Math.min(getHeight(),g2.getHeight()),
                Math.min(getWidth(),g2.getWidth()));
        
        for(int y = 0; y < ret.getHeight(); y++){
            for(int x = 0; x < ret.getWidth(); x++){
                double avg = (getXY(x,y)+g2.getXY(x,y));
                avg/=2.0;
                ret.setXY(x,y,avg);
            }
        }
        return ret;
    }
    
    public Grid2d sum(Grid2d g2){
        Grid2d ret = new Grid2d(
                Math.min(getHeight(),g2.getHeight()),
                Math.min(getWidth(),g2.getWidth()));
        
        for(int y = 0; y < ret.getHeight(); y++){
            for(int x = 0; x < ret.getWidth(); x++){
                double sum = (getXY(x,y) + g2.getXY(x,y));
                ret.setXY(x,y,sum);
            }
        }
        return ret;
    }
    
    public void threash(){
        threash(max/8.0);
    }
    
    public void threashDiv(double divisor){
        threash(max/divisor);
    }
    
    public void threash(double threash){
        for(int y = 0; y < getHeight(); y++){
            for(int x = 0; x < getWidth(); x++){
                if(getXY(x,y)<threash){
                    setXY(x,y,0);
                }
            }
        }
    }
    
    @Override
    public String toString() {
        String rtrn = "";
        int maxL = String.valueOf((int)max).length();
        if(maxL == 0 ){
            maxL=3;
        }
        
        for(int y = 0; y < getHeight(); y++){
            for(int x = 0; x < getWidth(); x++){
                String value = String.valueOf((int)getXY(x,y));
                if(value.length()>maxL){
                    value = value.substring(0,3);
                }
                while(value.length()<maxL){
                    value="0"+value;
                }
                rtrn+=(value+" ");
            }
            rtrn+="\n";
        }
        return rtrn;
    }
    
    /**
     * Praportinate scaling from old max to new max.
     */
    public void revalue(double maxValue){
        if(Header._DEBUG) System.out.println(max);
        double ldmax = this.max;
        for(int y = 0; y < getHeight(); y++){
            for(int x = 0; x < getWidth(); x++){
                if(Header._DEBUG) System.out.print(getXY(x,y)+"+"+getXY(x,y)/ldmax+" ");
                setXY(x,y,((getXY(x,y)/ldmax)*maxValue));
                
            }
            if(Header._DEBUG) System.out.println();
        }
    }
    
    /**
     * Returns a <code>BufferedImage</code> 
     */
    public BufferedImage render(){
        BufferedImage img = new BufferedImage(
                getWidth(),
                getHeight(),
                BufferedImage.TYPE_INT_RGB);
        
        Grid2d g = new Grid2d(
                img.getHeight(null),
                img.getWidth(null));
        
        WritableRaster r = img.getRaster();
        
        double[] pixel = new double[3];
        
        for(int y = 0; y < g.getHeight(); y++){
            for(int x = 0; x < g.getWidth(); x++){
                pixel[0]=getXY(x,y);
                pixel[1]=pixel[0];
                pixel[2]=pixel[1];
                
                r.setPixel(x,y,pixel);
            }
        }
        return img;
    }

    /**
     * returns the actual grid
     * @return a 2 dim double array
     */
    public double[][] getGrid() {
        return world;
    }
    
    /**
     * returns the actual grid
     * @return a 2 dim double array
     */
    public int[][] getGridInt() {
        int [][] grid = new int[world.length][world[0].length];
        
        if(Header._DEBUG) System.out.println("Get int grid, max was "+max);
        double ldmax = this.max;
        for(int y = 0; y < getHeight(); y++){
            for(int x = 0; x < getWidth(); x++){
                if(Header._DEBUG) System.out.print(getXY(x,y)+"+"+getXY(x,y)/ldmax+" ");
                grid[y][x] = (int)((getXY(x,y)/ldmax)* 254.0 + 1); //round
                
            }
            if(Header._DEBUG) System.out.println();
        }
        
        
        return grid;
    }
    
    /**
     * this calculates the avg value of the grid
     * @return the avg value this grid
     */
    public double avgValue(){
        if(Header._DEBUG) System.out.println("Calculating Avg of Grid");
        double ldavg = 0;
        for(int y = 0; y < getHeight(); y++){
            for(int x = 0; x < getWidth(); x++){
                if(Header._DEBUG) System.out.print(getXY(x,y)+" ");
                ldavg += getXY(x,y);
                
            }
            if(Header._DEBUG) System.out.println();
        }
        System.out.print("Sum = "+ldavg);
        ldavg/=getHeight()*getWidth();
        System.out.println("Avg = "+ldavg);
        return ldavg;
    }
    
}