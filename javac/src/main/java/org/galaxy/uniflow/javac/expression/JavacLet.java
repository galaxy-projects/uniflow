package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniLet;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacLet extends JavacExpression<JCTree.LetExpr> implements UniLet {

    public JavacLet(JCTree.@NotNull LetExpr tree) {
        super(tree);
    }

    @Override
    public @NotNull UniList<@NotNull UniStatement> getDefinitions() {
        return new JavacList<>(
                () -> tree.defs,
                newList -> tree.defs = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
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
