package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniOperatorExpression;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacOperator<T extends JCTree.JCOperatorExpression> extends JavacExpression<T>
        implements UniOperatorExpression {

    public JavacOperator(@NotNull T tree) {
        super(tree);
    }

    @Override
    public void setOperator(@NotNull UniOperatorSignature operator) {
        tree.operator = JavacUtils.javac(operator);
    }

    @Override
    public @NotNull UniOperatorSignature getOperator() {
        return UniUtils.uni(tree.operator);
    }
}
