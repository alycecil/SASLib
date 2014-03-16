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
/*
 * Main.java
 * 
 * Created on Oct 16, 2007, 1:45:35 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SASLib;

/**
 * This class contains information on common error codes w/ in 
 * SASlib as well as the _DEBUG flag
 * 
 * @author Wil Cecil
 */
public abstract class Header {
    
    /**
     * Set this to true to display debug information.
     * <br><br>
     * Best set to false.
    */
    public static boolean _DEBUG = false;
    
    
    //ERRORS
    
    /**
     * Input file not specified
     */
    public static int NO_IN_FILE = -9101;
    
    /**
     * Output file not specified
     */
    public static int NO_OUT_FILE = -9102;
    
    
}
