package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniUnary;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacUnary extends JavacOperator<JCTree.JCUnary> implements UniUnary {

    public JavacUnary(JCTree.@NotNull JCUnary tree) {
        super(tree);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        tree.arg = JavacUnwrapper.unwrap(expression);
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(tree.arg);
    }
}
