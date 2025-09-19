package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.types.UniClassType;

public class JavacClassType extends JavacExpressionType<JCTree.JCExpression, Type.ClassType> implements UniClassType {

    public JavacClassType(JCTree.JCIdent expression, Type.ClassType type) {
        super(expression, type);
    }

    public JavacClassType(JCTree.JCFieldAccess expression, Type.ClassType type) {
        super(expression, type);
    }
}
