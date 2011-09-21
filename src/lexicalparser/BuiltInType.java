/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalparser;

/**
 *
 * @author laurent
 */
public abstract class BuiltInType extends Calculable {
    abstract public Object getNativeValue();
    @Override
    public Object eval(){
        return this;
    }
    @Override
    public Calculable getSimplifiedCalculable() {
        return this;
    }
}
