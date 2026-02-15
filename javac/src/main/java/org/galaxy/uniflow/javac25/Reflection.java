package org.galaxy.uniflow.javac25;

import org.galaxy.uniflow.api.factories.UniConstants;

public class Reflection {

    public static final Class<?> TREE_MAKER;
    public static final Class<?> EXPRESSION_TYPE;

    public static final Class<?> MODULE_IMPORT;

    static {
        try {
            TREE_MAKER = Class.forName("com.sun.tools.javac.tree.TreeMaker");
            EXPRESSION_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCExpression");

            MODULE_IMPORT = Class.forName("com.sun.tools.javac.tree.JCTree$JCModuleImport");
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
