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
TextBoxWriter
Purpose:
An output stream the will write text to a Swing TextArea
Coders :
Wil Cecil
Created:
December 10, 2006
Change Log: 
 */
package SASLib.Util.GUI;

import java.io.*;

/**
 * An output stream the will write text to a Swing TextArea
 * @author Wil Cecil
 */
public class TextBoxWriter extends Writer {

    private javax.swing.JTextArea txtBox;
    private boolean open;

    /**
     * Default Constructor.
     * @param ptxtBox
     */
    TextBoxWriter(javax.swing.JTextArea ptxtBox) {
        txtBox = ptxtBox;
        open = true;
    }

    /**
     * Write a portion of an array of characters.
     *
     * @param  cbuf  Array of characters
     * @param  off   Offset from which to start writing characters
     * @param  len   Number of characters to write
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(char cbuf[], int off, int len) throws IOException {
        if (open) {
            txtBox.setText(txtBox.getText() + new String(cbuf, off, len));
        } else {
            throw new IOException("Stream Closed " + this.toString());
        }
    }

    /**
     * Write a String to the message box.
     *
     * @param  s    a String
     *
     * @exception  IOException  If an I/O error occurs
     */
    @Override
    public void write(String s) throws IOException {
        if (open) {
            txtBox.setText(txtBox.getText() + s);
        } else {
            throw new IOException("Stream Closed " + this.toString());
        }
    }

    /**
     * Does Nothing.
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void flush() throws IOException {
        if (open) {
        //does nothing
        } else {
            throw new IOException("Stream Closed " + this.toString());
        }
    }

    /**
     * Close the stream, flushing it first.  Once a stream has been closed,
     * further write() or flush() invocations will cause an IOException to be
     * thrown.  Closing a previously-closed stream, however, has no effect.
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void close() throws IOException {
        open = false;
    }
}
