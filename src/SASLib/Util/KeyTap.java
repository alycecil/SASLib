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
    KeyTap
Purpose:
    A KeyListner that can tell you all keys tapped at the current time
Coders :
    Wil Cecil
Created:
    Feb 11, 2008
Change Log: 
 */

package SASLib.Util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This is a KeyListner that can tell you all keys tapped at the current time.
 * @author Wil Cecil
 */
public class KeyTap implements KeyListener {

    int offset = 0;
    private boolean[] keyDown;

    /**
     * Calls this(KeyEvent.KEY_LAST-KeyEvent.KEY_FIRST, -KeyEvent.KEY_FIRST)
     * Works Pretty Well
     */
    public KeyTap() {
        this(1024,512);
    }

    /**
     * uses param1 as keydown grid
     * @param keyDown
     */
    public KeyTap(boolean[] keyDown) {
        this.keyDown = keyDown;
    }
    
    /**
     * Calls this(Keys, 0);
     * @param keys
     */
    public KeyTap(int keys) {
        this(keys, 0);
    }
    
    /**
     * BEST Contrucor
     * @param keys
     * @param offset
     */
    public KeyTap(int keys, int offset) {
        this.keyDown = new boolean[keys];
        this.offset = offset;
        
        System.out.println(keys + " " + offset);
        
    }
    

    /**
     * Trigger for keytyped, does nothing
     * @param e
     */
    public void keyTyped(KeyEvent e) {
    //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Tries and sets key pressed to true
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        try {
            keyDown[e.getKeyCode()+offset] = true;
        //System.out.println("Key Pressed "+ e.getKeyCode());
        } catch (Exception exc) {
            System.out.println("Key Press Error:" + e.getKeyCode() + " for " + exc.getMessage());
        }
    }

    /**
     * Tries and sets key released to false
     * @param e
     */
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        try {
            keyDown[e.getKeyCode()+offset] = false;
        } catch (Exception exc) {
            System.out.println("Key Release Error:" + e.getKeyCode() + " for " + exc.getMessage());
        }
    }
    
    /**
     * checks if a key is down
     * @param KeyCode
     * @return boolean from keypress grid w/ index of KeyCode+Offset
     */
    public boolean isPressed(int KeyCode){
        return keyDown[KeyCode+offset];
    }
    
}
