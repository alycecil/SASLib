/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SASLib.Geom;

/**
 * 
 * @author Wil Cecil
 */
public interface Result {
    /**
     * Returns the Count of the number of results, for a one to one function 
     * this will be one, for a parametric it will be two.
     */
    public int numberOfValues();
    
    /**
     * gets the (num)th result.
     * @param num
     * @return double
     * @throws IndexOutOfBoundsException this occurs when num is less than zero 
     * or greater than numberOfValues()-1
     */
    public double getResult(int num) throws IndexOutOfBoundsException;
    
}
