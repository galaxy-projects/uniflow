package org.galaxy.uniflow.javac21;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.factories.UniConstants;

@SuppressWarnings("unchecked")
public class Reflection {

    public static final Class<?> TREE_MAKER;
    public static final Class<?> TREE_TYPE;
    public static final Class<?> EXPRESSION_TYPE;
    public static final Class<?> PATTERN_TYPE;
    public static final Class<?> LIST_TYPE;

    public static final Class<?> CASE_KIND_TYPE;
    public static final Class<?> CASE_TYPE;

    public static final Class<? extends JCTree.JCCaseLabel> CASE_LABEL_TYPE;
    public static final Class<?> CONSTANT_CASE_LABEL_TYPE;
    public static final Class<?> PATTERN_CASE_LABEL_TYPE;

    public static final Class<?> BINDING_PATTERN_TYPE;
    public static final Class<?> ANY_PATTERN_TYPE;
    public static final Class<?> RECORD_PATTERN_TYPE;

    static {
        try {
            TREE_MAKER = Class.forName("com.sun.tools.javac.tree.TreeMaker");
            TREE_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree");
            EXPRESSION_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCExpression");
            PATTERN_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCPattern");
            LIST_TYPE = Class.forName("com.sun.tools.javac.util.List");

            CASE_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCCase");
            CASE_KIND_TYPE = Class.forName("com.sun.source.tree.CaseTree$CaseKind");

            CASE_LABEL_TYPE = (Class<? extends JCTree.JCCaseLabel>) Class.forName(
                    "com.sun.tools.javac.tree.JCTree$JCCaseLabel");
            CONSTANT_CASE_LABEL_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCConstantCaseLabel");
            PATTERN_CASE_LABEL_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCPatternCaseLabel");

            BINDING_PATTERN_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCBindingPattern");
            ANY_PATTERN_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCAnyPattern");
            RECORD_PATTERN_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCRecordPattern");
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
