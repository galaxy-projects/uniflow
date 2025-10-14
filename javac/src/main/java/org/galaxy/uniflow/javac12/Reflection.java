package org.galaxy.uniflow.javac12;

public class Reflection {

    public static final Class<?> SWITCH_EXPRESSION;

    static {
        try {
            SWITCH_EXPRESSION = Class.forName("com.sun.tools.javac.tree.JCTree$JCSwitchExpression");
        } catch (Throwable e) {
            throw new UnsupportedOperationException("Not supported in this java version");
        }
    }
}
