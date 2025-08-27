package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniReturn;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacReturn extends JavacElement<JCTree.JCReturn> implements UniReturn {

    public JavacReturn(JCTree.@NotNull JCReturn tree) {
        super(tree);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        tree.expr = JavacUtils.javac(expression);
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniUtils.uni(tree.expr);
    }
}
