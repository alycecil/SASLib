package SASLib.Util;
// This is the Val class, its purpose is to get the numeric values from a 
// String or Byte[].
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
		val
	Purpose:
		To get the numeric values from a String or Byte[].
	Extra:
		This is part of the piroLibs Library (herby know as "Library") and is to be treated a single 	
		part of the "Library" as a whole, read the GNU for more information.
	Coders :
		PiroGothe
	Created:
		1/24/2005
	Change Log: 
		1/24/2005 : Adapted from Will Cecil's TerminalServices readDouble(); (PiroGothe)
		1/24/2005 : Added negative detection (PiroGothe)
		1/24/2005 : Code clean up (PiroGothe)
                7/9/2007  : Added class to SASLib (Wil Cecil)
                          : Rearanged Code and changed some ifs to else ifs
*/

/**
 * To get the numeric values from a String or Byte[].
 * @author Wil Cecil
 */
abstract public class Val{
	static public double VAL(byte[] intake){
		/**	This removes all non-numeric from 
			byte[] intake and returns as double.
		*/
		byte[] tIntake = new byte[intake.length];	//temp array for formatting
		int curIndex=0;					//currentMax in tIntake
		boolean hasDot=false;
		for(int i = 0; i<intake.length;i++){		//loop all bytes w/ values
                        if(intake[i]>47&&intake[i]<58){		//is valid numeric
				tIntake[curIndex]=intake[i];	//append
				curIndex++;			//increment num of items
			}
                        else if(!hasDot&&intake[i]==46){	//no dot and input is dot
				hasDot=true;			//you now have dot  
				tIntake[curIndex]=intake[i];	//append dot
				curIndex++;			//increment num of items
			}					//btw: dot == . == 46
                        
                        else if(intake[i]==45&&curIndex==0){	//negative sign before numbers
				tIntake[curIndex]=intake[i];	//append neg sign
				curIndex++;			//increment num of items
			}
		}
		intake = tIntake; 				//old == formatted : 1 
		tIntake=null;					//release tIntake
		String readable = new String(intake,0,intake.length);
		readable=readable.trim();
		try{
			return Double.parseDouble(readable);	//return imputed formatted int
		}catch(NumberFormatException e){		//catch no input
			//System.out.println(e);		//REMOVED, Bad For End User
			return 0;				//Return 0 for empty string
		}
	}
	static public double VAL(String intake){
		return VAL(intake.getBytes());			//Do VAL(byte[]) w/ intake converted to byte[]
	}
}