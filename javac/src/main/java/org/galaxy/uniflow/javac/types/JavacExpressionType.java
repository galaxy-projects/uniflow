package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;

public class JavacExpressionType<J extends JCTree.JCExpression, T extends Type> extends JavacType<J, T> {

    public JavacExpressionType(J expression, T type) {
        super(expression, type);
    }
}
