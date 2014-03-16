
package SASLib.Geom;

/**
 *
 * @author Wil Cecil
 */
public interface Function {
    /**
     * this evaluates the function at the dependent variable
     * @param dependent
     * @return Result object of this call
     */
    public Result evaluate(double dependent);
    
}
