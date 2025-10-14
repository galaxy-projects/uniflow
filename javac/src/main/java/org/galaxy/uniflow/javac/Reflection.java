package org.galaxy.uniflow.javac;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.factories.UniConstants;

@SuppressWarnings("unchecked")
public class Reflection {

    public static final Class<?> TREE_MAKER;
    public static final Class<JCTree.JCExpression> EXPRESSION_TYPE;
    public static final Class<?> LIST_TYPE;

    public static final Class<?> CASE_TYPE;
    public static final Class<? extends JCTree.JCCaseLabel> CASE_LABEL_TYPE;

    static {
        try {
            TREE_MAKER = Class.forName("com.sun.tools.javac.tree.TreeMaker");

            EXPRESSION_TYPE = (Class<JCTree.JCExpression>) Class.forName(
                    "com.sun.tools.javac.tree.JCTree$JCExpression");
            LIST_TYPE = Class.forName("com.sun.tools.javac.util.List");

            CASE_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCCase");
            CASE_LABEL_TYPE = (Class<? extends JCTree.JCCaseLabel>) Class.forName(
                    "com.sun.tools.javac.tree.JCTree$JCCaseLabel");
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
