package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniBinary;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacBinary extends JavacOperator<JCTree.JCBinary> implements UniBinary {

    public JavacBinary(JCTree.@NotNull JCBinary tree) {
        super(tree);
    }

    @Override
    public void setLeftOperand(@NotNull UniExpression leftOperand) {
        tree.lhs = JavacUtils.javac(leftOperand);
    }

    @Override
    public @NotNull UniExpression getLeftOperand() {
        return UniUtils.uni(tree.lhs);
    }

    @Override
    public void setRightOperand(@NotNull UniExpression rightOperand) {
        tree.rhs = JavacUtils.javac(rightOperand);
    }

    @Override
    public @NotNull UniExpression getRightOperand() {
        return UniUtils.uni(tree.rhs);
    }
}
