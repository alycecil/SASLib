package SASLib.Util;
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
/**
 * Title: ASCII Purpose: Render an image with ascii characters instead of pixels
 * Coders : Wil Cecil Created: July 9, 2007, 5:35 PM Change Log:
 */

/*
 *
 * using 3x2 pixels per sampling, this method is not used at this time.
 ..  ..  ..  ..  ..  ..  ..  ..  ..  ..  ..  ..  ..  ..  ..  ..
 ..  ..  ..  ..  .@  .@  .@  .@  @.  @.  @.  @.  @@  @@  @@  @@
 ..  .@  @.  @@  ..  .@  @.  @@  ..  .@  @.  @@  ..  .@  @.  @@
 ,   .   _   -   i   v   g   -   c   i   s   =   e   z   m
 
 .@  .@  .@  .@  .@  .@  .@  .@  .@  .@  .@  .@  .@  .@  .@  .@
 ..  ..  ..  ..  .@  .@  .@  .@  @.  @.  @.  @.  @@  @@  @@  @@
 ..  .@  @.  @@  ..  .@  @.  @@  ..  .@  @.  @@  ..  .@  @.  @@
 '   !   /   2   !   ]   /   d   /   (   /   K   Y   4   Z   W
 
 @.  @.  @.  @.  @.  @.  @.  @.  @.  @.  @.  @.  @.  @.  @.  @.
 ..  ..  ..  ..  .@  .@  .@  .@  @.  @.  @.  @.  @@  @@  @@  @@
 ..  .@  @.  @@  ..  .@  @.  @@  ..  .@  @.  @@  ..  .@  @.  @@
 `   \   |   L   \   \   )   G   !   t   [   b   +   N   D   W
 
 @@  @@  @@  @@  @@  @@  @@  @@  @@  @@  @@  @@  @@  @@  @@  @@
 ..  ..  ..  ..  .@  .@  .@  .@  @.  @.  @.  @.  @@  @@  @@  @@
 ..  .@  @.  @@  ..  .@  @.  @@  ..  .@  @.  @@  ..  .@  @.  @@
 ~   T   7   X   V   Y   Z   8   f   5   P   K   *   M   A   @
 */
/**
 * Draw an image with ascii characters instead of pixels.
 *
 * @author Wil Cecil
 */
public class ASCII {

    /**
     * Takes average of all used colors
     */
    public static final int Greyscale_Average = 0;
    /**
     * Takes max of all used colors
     */
    public static final int Greyscale_Intence = 1;
    /**
     * Takes Average of sample, only used in sample sizes more than 1x1
     */
    public static final int Sample_Average = 0;
    /**
     * Takes Max of sample, only used in sample sizes more than 1x1
     */
    public static final int Sample_Intence = 1;
    /**
     * Takes Averages the Running Max of Sample and the next pos, only used in
     * sample sizes more than 1x1
     */
    private static final int Sample_Mixed = 2; //NOT YET IMPLEMENTED
    /**
     * Lighter <- viewing light characters on a dark background -> Darker Darker
     * <- viewing dark characters on a light background -> Lighter
     */
    private static final String fastASCII[] = {" ", ".", ":", ",", ";", "8", "B", "#", "M"};
    //{" ","'",".",",","!",":",";","-","v","z",
    // "o","+","a","Z","V","A","8","B","#","M",
    //};
    /*
     *    ????????????????
     *    ????????????????
     *    ????????????????
     *    ????????????????
     *    ::::;;;;!!!!8888....----mmmm
     *    ::::;;;;!!!!8888....----mmmm
     *    ::::;;;;!!!!8888....----mmmm
     *    ::::;;;;!!!!8888....----mmmm
     *vvvvaaaazzzz0000@@@@''''++++MMMM
     *vvvvaaaazzzz0000@@@@''''++++MMMM
     *vvvvaaaazzzz0000@@@@''''++++MMMM
     *vvvvaaaazzzz0000@@@@''''++++MMMM
     *VVVVAAAAZZZZoooo####****BBBB@@@@
     *VVVVAAAAZZZZoooo####****BBBB@@@@
     *VVVVAAAAZZZZoooo####****BBBB@@@@
     *VVVVAAAAZZZZoooo####****BBBB@@@@
     *
     88888888888888888:::88888:::::M888888888888888   8888
     88888888888888888:::88888::::M::;o*M*o;888888888    88
     88888888888888888:::8888:::::M:::::::::::88888888    8
     88888888888888888::::88::::::M:;:::::::::::888888888
     8888888888888888888:::8::::::M::aAa::::::::M8888888888       8
     88   8888888888::88::::8::::M:::::::::::::888888888888888 8888
     88  88888888888:::8:::::::::M::::::::::;::88:88888888888888888
     8  8888888888888:::::::::::M::"@@@@@@@"::::8w8888888888888888
     88888888888:888::::::::::M:::::"@a@":::::M8i888888888888888
     8888888888::::88:::::::::M88:::::::::::::M88z88888888888888888
     8888888888:::::8:::::::::M88888:::::::::MM888!888888888888888888
     888888888:::::8:::::::::M8888888MAmmmAMVMM888*88888888   88888888
     */
    private static final String fastASCIICRAZY[] = {" ", ".", "'", "`", ",", "^", ":", "\"", ";", "~",
        "-", "_", "+", "<", ">", "i", "!", "l", "I", "?",
        "/", "\\", "|", "(", ")", "1", "{", "}", "[", "]",
        "r", "c", "v", "u", "n", "x", "z", "j", "f", "t",
        "L", "C", "J", "U", "Y", "X", "Z", "O", "0", "Q",
        "*", "W", "M", "B", "8", "&", "%", "$", "#", "@"
    };
    private static final String newLine = "\n";

    /**
     * This converts a 3 dim array [y][x][red, green, blue] to a string
     */
    public static String asciiRender(int[][][] img, boolean invert, boolean useRed, boolean useGreen, boolean useBlue, int yPixelsPerSample, int xPixelsPerSample, int SampleMethod, int GreyScale_Method) {
        //Trun 3 dim to 2 dim {Grey Scale)
        int[][] greyScale;
        try { //Try to catch non rectangular image, ect.
            greyScale = new int[img.length][img[0].length];

            //Dividers is the number of colors to use in calc of average
            int dividers = 0;
            if (useRed) {
                dividers++;
            }
            if (useGreen) {
                dividers++;
            }
            if (useBlue) {
                dividers++;
            }
            if (dividers == 0) {
                throw new IllegalArgumentException("At least one color must be used to calc greyscale");
            }

            //:::GREY SCALE CALCULATIONS:::

            //AVERAGE
            if (GreyScale_Method == Greyscale_Average) {
                for (int x = 0; x < img.length; x++) {
                    for (int y = 0; y < img[x].length; y++) {
                        greyScale[x][y] = 0;
                        if (useRed) {
                            greyScale[x][y] += img[x][y][0];
                        }
                        if (useGreen) {
                            greyScale[x][y] += img[x][y][1];
                        }
                        if (useBlue) {
                            greyScale[x][y] += img[x][y][2];
                        }

                        greyScale[x][y] /= dividers; //Found Grey Scale based on average of colors
                    }
                }
            } //MAX
            else if (GreyScale_Method == Greyscale_Intence) {
                for (int x = 0; x < img.length; x++) {
                    for (int y = 0; y < img[x].length; y++) {
                        greyScale[x][y] = 0;
                        if (useRed) {
                            greyScale[x][y] = Math.max(img[x][y][0], greyScale[x][y]);
                        }
                        if (useGreen) {
                            greyScale[x][y] = Math.max(img[x][y][1], greyScale[x][y]);
                        }
                        if (useBlue) {
                            greyScale[x][y] = Math.max(img[x][y][2], greyScale[x][y]);
                        }
                    }
                }
            } //OTHERWISE
            else {
                throw new IllegalArgumentException("Invalid greyscale calculation method");
            }

        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("IndexOutOfBounds; Image must be rectangular or with 1st x row being the smallest");
        }

        //Do 2dim work
        return asciiRender(greyScale, invert, yPixelsPerSample, xPixelsPerSample, SampleMethod);
    }

    /**
     * This converts a 2 dim array [y][x] to a string Sample Size 3 x 7 is
     * pretty good
     */
    public static String asciiRender(int[][] img, boolean invert, int xPixelsPerSample, int yPixelsPerSample, int SampleMethod) {
        String render = "";

        //SELECT SAMPLE METHOD
        //this code must be FAST :: REASON this code is run thousands of times for even small images
        //ie a 256 x 256 with 3x3 sample gathers samples 7282 times with a loop of 9 inside each time.

        if (yPixelsPerSample > 1 || xPixelsPerSample > 1) { //SAMPLING REQUIRED

            int[][] sample = new int[img.length / yPixelsPerSample][img[0].length / xPixelsPerSample];

            //AVG
            if (SampleMethod == Sample_Average) {
                //Predefine to keep calsulations to minimum
                int sampleSpace = yPixelsPerSample * xPixelsPerSample;

                //predefine so space is saved; saving time
                int posx = 0;
                int posy = 0;
                for (int x = 0; x < sample.length; x++) {
                    for (int y = 0; y < sample[x].length; y++) {
                        int thisSampleSpace = sampleSpace;
                        //GATHER SAMPLE DATA
                        for (int sampleX = 0; sampleX < yPixelsPerSample; sampleX++) {
                            for (int sampleY = 0; sampleY < xPixelsPerSample; sampleY++) {
                                try {
                                    sample[x][y] += img[posx + sampleX][posy + sampleY];
                                } catch (Exception e) {
                                    thisSampleSpace--; //this spot skipped, dont include in avg
                                    continue;
                                }
                            }
                        }
                        sample[x][y] /= thisSampleSpace;

                        //DO ASCII CONVERT
                        render += convert(sample[x][y], invert);

                        //Update posY for next loop
                        posy += xPixelsPerSample;
                    }

                    render += newLine; //New line
                    posy = 0; //Reset posY
                    posx += yPixelsPerSample; //increment posX for next loop
                }
            } //MAX
            else if (SampleMethod == Sample_Intence) {

                //predefine so space is saved; saving time
                int posx = 0;
                int posy = 0;
                for (int x = 0; x < sample.length; x++) {
                    for (int y = 0; y < sample[x].length; y++) {
                        //GATHER SAMPLE DATA
                        for (int sampleX = 0; sampleX < yPixelsPerSample; sampleX++) {
                            for (int sampleY = 0; sampleY < xPixelsPerSample; sampleY++) {
                                try {
                                    sample[x][y] = Math.max(sample[x][y], img[posx + sampleX][posy + sampleY]);
                                } catch (Exception e) {
                                    System.out.println("Error at " + x + " " + y + " with " + img[posx + sampleX][posy + sampleY]);
                                    render += "E";
                                    continue;
                                }
                            }
                        }

                        //DO ASCII CONVERT
                        render += convert(sample[x][y], invert);

                        //Update posY for next loop
                        posy += xPixelsPerSample;
                    }

                    render += newLine; //New line
                    posy = 0; //Reset posY
                    posx += yPixelsPerSample; //increment posX for next loop
                }
            } //Illegal Sampling Method
            else {
                throw new IllegalArgumentException("Invalid sampling method");
            }
        } else { //NO SAMPLEING REQUIRED :: REASON sample size is 1 x 1
            //CODE MUST BE ULTRA SLEEK!
            //256 x 256 image = 65538 calculations

            for (int x = 0; x < img.length; x++) {
                for (int y = 0; y < img[x].length; y++) {
                    try {
                        render += convert(img[x][y], invert);
                    } catch (Exception e) {
                        System.out.println("Error at " + x + " " + y + " with " + img[x][y]);
                        render += "E";
                    }
                }
                render += newLine; //New line
            }

        }
        return render;
    }

    /**
     * This Method is the fast convert to ascii method
     */
    private static String convert(int value, boolean invert) {
        value = (int) ((((double) value) / 256.0) * (fastASCII.length));
        if (invert) {
            return fastASCII[fastASCII.length - 1 - value];
        }
        return fastASCII[value];
    }

    /**
     * Creates a gradient to test the code with
     */
    public static int[][][] testImage(int width, int height) {
        int[][][] img = new int[width][height][3];

        int valx = 256 / width;
        int valy = 256 / height;

        for (int x = 0; x < img.length; x++) {
            for (int y = 0; y < img[x].length; y++) {
                img[x][y][0] = valx * x;
                img[x][y][1] = valy * y;
                img[x][y][2] = (valx * x + valy * y) / 2;
            }
        }

        return img;
    }
}
