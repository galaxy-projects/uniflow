package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.javac.JavacElement;
import org.jetbrains.annotations.NotNull;

public abstract class JavacExpression<T extends JCTree.JCExpression> extends JavacElement<T> implements UniExpression {

    public JavacExpression(@NotNull T tree) {
        super(tree);
    }
}
