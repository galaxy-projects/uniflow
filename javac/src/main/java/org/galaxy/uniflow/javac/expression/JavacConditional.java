package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniConditional;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacConditional extends JavacExpression<JCTree.JCConditional> implements UniConditional {

    public JavacConditional(JCTree.@NotNull JCConditional tree) {
        super(tree);
    }

    @Override
    public void setCondition(@NotNull UniExpression condition) {
        tree.cond = JavacUnwrapper.unwrap(condition);
    }

    @Override
    public @NotNull UniExpression getCondition() {
        return UniflowWrapper.wrap(tree.cond);
    }

    @Override
    public void setTrueExpression(@NotNull UniExpression expression) {
        tree.truepart = JavacUnwrapper.unwrap(expression);
    }

    @Override
    public @NotNull UniExpression getTrueExpression() {
        return UniflowWrapper.wrap(tree.truepart);
    }

    @Override
    public void setFalseExpression(@NotNull UniExpression expression) {
        tree.falsepart = JavacUnwrapper.unwrap(expression);
    }

    @Override
    public @NotNull UniExpression getFalseExpression() {
        return UniflowWrapper.wrap(tree.falsepart);
    }
}
