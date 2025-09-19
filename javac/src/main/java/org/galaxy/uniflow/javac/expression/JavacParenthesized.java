package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniParenthesized;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacParenthesized extends JavacExpression<JCTree.JCParens> implements UniParenthesized {

    public JavacParenthesized(JCTree.@NotNull JCParens tree) {
        super(tree);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        tree.expr = JavacUnwrapper.unwrap(expression);
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(tree.expr);
    }
}
