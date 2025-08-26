package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniConditional;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacConditional extends JavacExpression<JCTree.JCConditional> implements UniConditional {

    public JavacConditional(JCTree.@NotNull JCConditional tree) {
        super(tree);
    }

    @Override
    public void setCondition(@NotNull UniExpression condition) {
        tree.cond = JavacUtils.javac(condition);
    }

    @Override
    public @NotNull UniExpression getCondition() {
        return UniUtils.uni(tree.cond);
    }

    @Override
    public void setTrueExpression(@NotNull UniExpression expression) {
        tree.truepart = JavacUtils.javac(expression);
    }

    @Override
    public @NotNull UniExpression getTrueExpression() {
        return UniUtils.uni(tree.truepart);
    }

    @Override
    public void setFalseExpression(@NotNull UniExpression expression) {
        tree.falsepart = JavacUtils.javac(expression);
    }

    @Override
    public @NotNull UniExpression getFalseExpression() {
        return UniUtils.uni(tree.falsepart);
    }
}
