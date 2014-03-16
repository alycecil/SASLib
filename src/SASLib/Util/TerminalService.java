package SASLib.Util;
// This is the TerminalService class, its purpose is to get input in 
// a command line enviroment.
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
TerminalService
Purpose:
To get input in a command line enviroment.
Extra:
This is part of the piroLibs Library (herby know as "Library") and is to be treated a single 	
part of the "Library" as a whole, read the GNU for more information.
Coders :
PiroGothe
Created:
1/24/2005
Change Log: 
1/24/2005 : Created from scratch (PiroGothe)
1/24/2005 : Code clean up (PiroGothe)
1/24/2005 : Code Shrinking 200+ lines to lines 46 to 79, retains funtion and now uses val class (PiroGothe)
7/9/2007  : Added class to SASLib (Wil Cecil)
: Fixed Code
 */
import java.io.*;

/**
 * To get input in a command line enviroment.
 * @author Wil Cecil
 */
public class TerminalService {

    static public String readString() {
        try {
            byte[] intake = new byte[1028];			//Create intake array
            int inputAmt = System.in.read(intake, 0, 1028);	//Get input and number of chars inputed
            //Clr last 1 from intake[] :Reason: Do not want Carage return and line feed
            intake[inputAmt] = 0;
            inputAmt--;

            String readable = new String(intake, 0, inputAmt);  //Convert bytes to readable text
            return readable;                                    //return inputed formated String
        } catch (IOException e) {
            System.out.println(e);
            return "~ERROR OCCURED WITH INPUT~";
        }
    }

    static public char readChar() {
        //Call readString() then get first character
        return readString().charAt(0);				//return 1st Char input : removed ugly skip(available()) 
    }

    static public double readDouble() {
        return Val.VAL(readString());				//convert String input to double
    }

    static public int readInt() {
        return (int) readDouble();				//call readDouble() then cast to Int
    }
}
