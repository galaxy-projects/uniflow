package org.galaxy.uniflow.javac;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.factories.UniConstants;

@SuppressWarnings("unchecked")
public class Reflection {

    public static final Class<JCTree.JCExpression> EXPRESSION;

    static {
        try {
            EXPRESSION = (Class<JCTree.JCExpression>) Class.forName("com.sun.tools.javac.tree.JCTree$JCExpression");
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
