package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniAssignment;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacAssignment extends JavacExpression<JCTree.JCAssign> implements UniAssignment {

    public JavacAssignment(JCTree.@NotNull JCAssign tree) {
        super(tree);
    }

    @Override
    public void setVariable(@NotNull UniExpression variable) {
        tree.lhs = JavacUnwrapper.unwrap(variable);
    }

    @Override
    public @NotNull UniExpression getVariable() {
        return UniflowWrapper.wrap(tree.lhs);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        tree.rhs = JavacUnwrapper.unwrap(expression);
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(tree.rhs);
    }
}
