package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniReturn;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacReturn extends JavacStatement<JCTree.JCReturn> implements UniReturn {

    public JavacReturn(JCTree.@NotNull JCReturn tree) {
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
