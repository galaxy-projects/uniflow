package org.galaxy.uniflow.javac12;

import com.sun.tools.javac.tree.JCTree;

@SuppressWarnings("unchecked")
public class Reflection {

    public static final Class<?> TREE_MAKER;
    public static final Class<?> TREE_TYPE;

    public static final Class<?> SWITCH_EXPRESSION_TYPE;
    public static final Class<?> YIELD_TYPE;

    public static final Class<?> CASE_KIND;
    public static final Class<?> LIST_TYPE;
    public static final Class<? extends JCTree.JCCaseLabel> CASE_LABEL_TYPE;

    static {
        try {
            TREE_MAKER = Class.forName("com.sun.tools.javac.tree.TreeMaker");
            TREE_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree");
            LIST_TYPE = Class.forName("com.sun.tools.javac.util.List");

            SWITCH_EXPRESSION_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCSwitchExpression");
            YIELD_TYPE = Class.forName("com.sun.tools.javac.tree.JCTree$JCYield");

            CASE_KIND = Class.forName("com.sun.source.tree.CaseTree$CaseKind");
            CASE_LABEL_TYPE = (Class<? extends JCTree.JCCaseLabel>) Class.forName(
                    "com.sun.tools.javac.tree.JCTree$JCCaseLabel");
        } catch (Throwable e) {
            throw new UnsupportedOperationException("Not supported in this java version");
        }
    }
}
