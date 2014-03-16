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
package SASLib.GL;

import SASLib.Geom.*;
import SASLib.Util.RotMath;
import java.awt.Rectangle;
import javax.media.opengl.GL2;

/**
 * This object is used to render shapes
 * @author Wil Cecil
 */
public class ShapeRenderer {

    /**
     * Render bounding Box
     * @param s shape
     * @param gl GL object
     * @param x -x transform
     * @param y -y transform
     * @param zoom -z transforming
     */
    public static synchronized void renderBounds(Shape s, GL2 gl, double x, double y, double zoom) {
        //get bounds
        Rectangle r = s.getBounds();

        //call rectangle renderer
        render(r, gl, x, y, zoom);
    }

    /**
     * Render Box
     * @param r rectangle
     * @param gl GL object
     * @param x -x transform
     * @param y -y transform
     * @param zoom -z transforming
     */
    public static synchronized void render(Rectangle r, GL2 gl, double x, double y, double zoom) {
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" to another position
        gl.glTranslated(-x, -y, -zoom);

        // Draw A Quad
        gl.glBegin(GL2.GL_LINE_LOOP);

        gl.glVertex3d(r.x, r.y, 0.0f);  // Top Left
        //gl.glTexCoord2d(cords.left(), cords.top());

        gl.glVertex3d(r.x + r.width, r.y, 0.0f);   // Top Right
        //gl.glTexCoord2d(cords.right(), cords.top());

        gl.glVertex3d(r.x + r.width, r.y + r.height, 0.0f);  // Bottom Right
        //gl.glTexCoord2d(cords.right(), cords.bottom());

        gl.glVertex3d(r.x, r.y + r.height, 0.0f); // Bottom Left
        //gl.glTexCoord2d(cords.left(), cords.bottom());

        // Done Drawing The Rect
        gl.glEnd();

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public static void render(Triangle t, GL2 gl, double x, double y, double zoom, boolean wireframe) {
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" to another position
        gl.glTranslated(-x, -y, -zoom);


        // Draw A TRIANGLE
        if (wireframe) {
            gl.glBegin(GL2.GL_LINES);

            Line[] lines = t.getLines();
            for (int i = 0; i < lines.length; i++) {
                gl.glVertex3d(lines[i].getPnt1().x, lines[i].getPnt1().y, 0.0f);  // draws line points
                gl.glVertex3d(lines[i].getPnt2().x, lines[i].getPnt2().y, 0.0f);  // draws line points
            }
        } else {
            gl.glBegin(GL2.GL_TRIANGLES);

            Line[] lines = t.getLines();
            for (int i = 0; i < lines.length; i++) {
                gl.glVertex3d(lines[i].getPnt1().x, lines[i].getPnt1().y, 0.0f);  // draws corner points
            }
        }

        // Done Drawing The Triangle
        gl.glEnd();

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public static void render(Circle c, GL2 gl, double circleDivisions, double x, double y, double zoom, boolean wireframe) {
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" to another position
        gl.glTranslated(c.getCenter().getX() - x, c.getCenter().getY() - y, -zoom);


        Math.max(circleDivisions, 3.0);
        circleDivisions = 360.0 / circleDivisions;
        double r = c.getRadius();
        if (wireframe) {
            gl.glBegin(GL2.GL_LINE_LOOP);

            for (double i = 0; i < 360.0; i += circleDivisions) {
                gl.glVertex3d(r * RotMath.CoSine(i), r * RotMath.Sine(i), 0.0f);  // draws line points
            }
        } else {
            gl.glBegin(GL2.GL_TRIANGLES);

            for (double i = 0; i < 360.0; i += circleDivisions) {
                gl.glVertex3d(0, 0, 0); //center
                gl.glVertex3d(r * RotMath.CoSine(i), r * RotMath.Sine(i), 0.0f);
                gl.glVertex3d(r * RotMath.CoSine(i + circleDivisions), r * RotMath.Sine(i + circleDivisions), 0.0f);
            }
        }

        // Done Drawing The Triangle
        gl.glEnd();

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public static void render(Quadrilateral r, GL2 gl, double x, double y, double zoom, boolean wireframe) {
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" to another position
        gl.glTranslated(-x, -y, -zoom);

        //get the points to draw from
        Point[] lines = r.getPoints();

        // Draw A Rhombus
        if (wireframe) {
            gl.glBegin(GL2.GL_LINES);

            //draw lines
            for (int i = 0; i < lines.length; i++) {
                gl.glVertex3d(lines[i].getX(), lines[i].getY(), 0.0f);  // draws line points
                gl.glVertex3d(lines[(i + 1) % lines.length].getX(), lines[(i + 1) % lines.length].getY(), 0.0f);  // draws line points
            }
        } else {
            gl.glBegin(GL2.GL_QUADS);

            for (int i = 0; i < lines.length; i++) {
                gl.glVertex3d(lines[i].getX(), lines[i].getY(), 0.0f);  // draws line points
            }
        }

        // Done Drawing The Shape
        gl.glEnd();

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public static void render(Point p, GL2 gl, double x, double y, double zoom) {
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" to another position
        gl.glTranslated(-x, -y, -zoom);

        //get the points to draw from

        gl.glBegin(GL2.GL_POINTS);

        //draw point
        gl.glVertex3d(p.getX(), p.getY(), 0.0f);  // draws point

        // Done Drawing The Point
        gl.glEnd();

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public static void render(Line l, GL2 gl, double x, double y, double zoom) {
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" to another position
        gl.glTranslated(-x, -y, -zoom);

        gl.glBegin(GL2.GL_LINES);

        //draw line    
        gl.glVertex3d(l.getPnt1().getX(), l.getPnt1().getY(), 0.0f);  // draws line points
        gl.glVertex3d(l.getPnt2().getX(), l.getPnt2().getY(), 0.0f);  // draws line points

        // Done Drawing The Shape
        gl.glEnd();

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }
    
    public static void drawGrid(double xMax, double yMax, GL2 gl, double x, double y, double zoom){
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" to another position
        gl.glTranslated(-x, -y, -zoom);
        
        //we're gonna draw ordered pairs of lines
        gl.glBegin(gl.GL_LINES);

        //Vert Line
        gl.glVertex2d(0, -yMax);
        gl.glVertex2d(0, yMax);

        //Hori Line
        gl.glVertex2d(-xMax, 0);
        gl.glVertex2d( xMax, 0);

        //end of drawong
        gl.glEnd();

        //all done
        gl.glFlush();
        
    }
    
    public static void drawGridBoxs(double xMax, double yMax, GL2 gl, double x, double y, double zoom){
        drawGrid(xMax, yMax, gl, x, y, zoom);
        
        double min = Math.min(xMax, yMax);
        for(int i = 1; i < min; i++){
            render(new Rectangle(-i,-i,2*i,2*i), gl, x, y, zoom);
        }
    }
}
