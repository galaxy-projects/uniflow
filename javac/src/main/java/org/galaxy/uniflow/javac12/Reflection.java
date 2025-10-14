package org.galaxy.uniflow.javac12;

public class Reflection {

    public static final Class<?> GUARD_PATTERN_TYPE;
    public static final Class<?> PARENTHESIZED_PATTERN_TYPE;

    static {
        try {
            GUARD_PATTERN_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCGuardPattern");
            PARENTHESIZED_PATTERN_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCParenthesizedPattern");
        } catch (Throwable e) {
            throw new UnsupportedOperationException("Not supported in this java version");
        }
    }
}
