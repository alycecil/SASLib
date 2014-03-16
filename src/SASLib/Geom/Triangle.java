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
    Triangle
Purpose:
    Handle all 3-gons
Coders :
    Wil Cecil
Created:
    Feb 24th 2008
Change Log: 
 */
package SASLib.Geom;

import java.awt.Rectangle;
import javax.media.opengl.GL2;

/**
 * Provide generic support for all triangles.
 * @author Wil Cecil
 */
public class Triangle implements Shape, Cloneable{

    /**
     * Theses are the 3 lines that make up the triangle
     */
    Line[] lines;
    Point center;
    protected boolean fresh;
    protected static double _mindif = 1.0;
    Rectangle bound;

    /**
     * This method makes a triangle from three points
     * @param p1 - point one
     * @param p2 - point two
     * @param p3 - point three
     */
    public Triangle(Point p1, Point p2, Point p3) {
        //creates line array
        lines = new Line[3];
        lines[0] = new Line(p1, p2, true);
        lines[1] = new Line(p2, p3, true);
        lines[2] = new Line(p3, p1, true);

        bound = new Rectangle(p1.getBigX(), p1.getBigY(), 0 , 0);

        bound.add(p2);
        bound.add(p3);

        //calculates center
        center = new Point((p1.x + p2.x + p3.x) / 3.0, (p1.y + p2.y + p3.y) / 3.0);

        //set fresh variable
        fresh = true;
    }

    /**
     * This method checks if a point is within the triangle
     * @param p Point to check if contained
     * @return boolean true if point is within the triangle
     */
    public boolean contains(Point p) {
        return CollisionHandler.contains(this, p);
    }

    //this is here to overide the default
    public boolean contains(double x, double y) {
        return contains(new Point(x, y));
    }

    /**
     * This method checks if a line is within or crosses a triangle
     * @param l the Line to check if contained
     * @return boolean true if line is within the triangle
     */
    public boolean intersects(Line l) {
        //Call Collision handler
        return CollisionHandler.checkCollision(this, l);
    }

    /**
     * This method checks if a line is within or crosses a triangle
     * @param t the Triangle to check if contained
     * @return boolean true if line is within the triangle
     */
    public boolean intersects(Triangle t) {
        //Call Collision Handler
        return CollisionHandler.checkCollision(this, t);
    }

    public void render(GL2 gl, double x, double y, double zoom) {
        // Reset the current matrix to the "identity"
        
        gl.glLoadIdentity();

        // Move the "drawing cursor" to another position
        gl.glTranslated(-x, -y, -zoom);


        // Draw A TRIANGLE
        //gl.glBegin(GL2.GL_LINES);
        gl.glBegin(GL2.GL_TRIANGLES);

        color(gl);

        for (int i = 0; i < lines.length; i++) {
            color(gl);
            gl.glVertex3d(lines[i].pnt1.x, lines[i].pnt1.y, 0.0f);  // draws line points
        //    gl.glVertex3d(lines[i].pnt2.x, lines[i].pnt2.y, 0.0f);  // draws line points
        }

        // Done Drawing The Quad
        gl.glEnd();

        // Flush all drawing operations to the graphics card
        gl.glFlush();

        //render bound rectangle
        //RectangleRender.render(bound, gl);

//        for (int i = 0; i < lines.length; i++) {
//                Rectangle boundline = lines[i].getBounds();
//                RectangleRender.render(boundline, gl);
//        }
    }

    /**
     * This method moves the triangle.
     * @param deltaX
     * @param deltaY
     */
    public void translate(double deltaX, double deltaY) {
        if (!fresh) {
            return;
        }

        //translate center
        center.x += deltaX;
        center.y += deltaY;

        //move lines by dx, dy
        for (int i = 0; i < lines.length; i++) {
            lines[i].pnt1.x += deltaX;
            lines[i].pnt1.y += deltaY;
            lines[i].pnt2.x += deltaX;
            lines[i].pnt2.y += deltaY;
        }

        //refresh bounds
        bound = new Rectangle(lines[lines.length-1].pnt1.getBigX(), lines[lines.length-1].pnt1.getBigY(), 0 , 0);
        for (int i = 0; i < lines.length; i++) {
            bound.add(lines[i].pnt1.getBigX(), lines[i].pnt1.getBigY());
        }
    }

    /**
     * This method moves the triangle.
     * @param deltaX
     * @param deltaY
     */
    public void translate(int deltaX, int deltaY) {
        translate((double) deltaX, (double) deltaY);
    }

    public void colide(Triangle t) {
        if (fresh && t.fresh) {
            fresh = false;
            t.fresh = false;
        }
    }

    private void color(GL2 gl) {
        if (fresh) {
            double dis = Math.abs(Math.sqrt(center.x*center.x+center.y*center.y));
            //gl.glColor3d(dis%50.0/50.0, dis, Math.random());
            //gl.glColor3d((center.x%39.0)/39.0, (center.y%16.0)/16.0,  Math.random());
            //gl.glColor3d(Math.abs(center.y)/((double)(bound.x+bound.width)), Math.abs(center.x)/((double)(bound.y+bound.height)), Math.random());    // Set the current drawing color to white
            gl.glColor3f(1.0f, 1.0f, 1.0f);    // Set the current drawing color to white
        } else {
            gl.glColor3f(.70f, .70f, .70f);    // Set the current drawing color to grey
        }
    }

    @Override
    public String toString() {
        StringBuffer ret = new StringBuffer(super.toString() + "\n");
        ret.append("\tcenter: " + center.toString() + "\n");
        for (int i = 0; i < lines.length; i++) {
            ret.append("\tpoint" + i + ": " + lines[i].pnt1.toString() + "\n");
        }
        ret.append("\thash: " + this.hashCode() + "\n");
        return ret.toString();
    }

    public Rectangle getBounds() {
        return bound.getBounds();
    }

    public Line[] getLines() {
        return lines;
    }
}
