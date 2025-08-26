package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniCompoundAssignment;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacCompoundAssignment extends JavacExpression<JCTree.JCAssignOp> implements UniCompoundAssignment {

    public JavacCompoundAssignment(JCTree.@NotNull JCAssignOp tree) {
        super(tree);
    }

    @Override
    public void setVariable(@NotNull UniExpression variable) {
        tree.lhs = JavacUtils.javac(variable);
    }

    @Override
    public @NotNull UniExpression getVariable() {
        return UniUtils.uni(tree.lhs);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        tree.rhs = JavacUtils.javac(expression);
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniUtils.uni(tree.rhs);
    }
}
