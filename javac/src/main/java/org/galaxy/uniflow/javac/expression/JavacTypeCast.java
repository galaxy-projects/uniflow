package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniTypeCast;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacTypeCast extends JavacExpression<JCTree.JCTypeCast> implements UniTypeCast {

    public JavacTypeCast(JCTree.@NotNull JCTypeCast tree) {
        super(tree);
    }

    @Override
    public void setType(@NotNull UniElement type) {
        tree.clazz = JavacUnwrapper.unwrap(type);
    }

    @Override
    public @NotNull UniElement getType() {
        return UniflowWrapper.wrap(tree.clazz);
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
