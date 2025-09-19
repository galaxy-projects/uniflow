package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniBinary;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacBinary extends JavacOperator<JCTree.JCBinary> implements UniBinary {

    public JavacBinary(JCTree.@NotNull JCBinary tree) {
        super(tree);
    }

    @Override
    public void setLeftOperand(@NotNull UniExpression leftOperand) {
        tree.lhs = JavacUnwrapper.unwrap(leftOperand);
    }

    @Override
    public @NotNull UniExpression getLeftOperand() {
        return UniflowWrapper.wrap(tree.lhs);
    }

    @Override
    public void setRightOperand(@NotNull UniExpression rightOperand) {
        tree.rhs = JavacUnwrapper.unwrap(rightOperand);
    }

    @Override
    public @NotNull UniExpression getRightOperand() {
        return UniflowWrapper.wrap(tree.rhs);
    }
}
