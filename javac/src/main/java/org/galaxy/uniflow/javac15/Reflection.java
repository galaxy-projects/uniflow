package org.galaxy.uniflow.javac15;

import org.galaxy.uniflow.api.factories.UniConstants;

public class Reflection {

    public static final Class<?> TREE_MAKER;
    public static final Class<?> VARIABLE_TYPE;
    public static final Class<?> TREE_TYPE;
    public static final Class<?> EXPRESSION_TYPE;
    public static final Class<?> INSTANCEOF_TYPE;

    public static final Class<?> CASE_TYPE;

    public static final Class<?> PATTERN_TYPE;
    public static final Class<?> GUARD_PATTERN_TYPE;
    public static final Class<?> BINDING_PATTERN_TYPE;
    public static final Class<?> PARENTHESIZED_PATTERN_TYPE;

    static {
        try {
            TREE_MAKER = Class.forName("com.sun.tools.javac.tree.TreeMaker");
            VARIABLE_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCVariableDecl");
            TREE_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree");
            EXPRESSION_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCExpression");
            INSTANCEOF_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCInstanceOf");

            CASE_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCCase");

            PATTERN_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCPattern");
            GUARD_PATTERN_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCGuardPattern");
            BINDING_PATTERN_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCBindingPattern");
            PARENTHESIZED_PATTERN_TYPE = Class.forName("om.sun.tools.javac.tree.JCTree$JCParenthesizedPattern");
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
