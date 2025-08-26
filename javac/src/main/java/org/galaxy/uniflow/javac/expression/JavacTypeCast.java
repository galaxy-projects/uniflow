package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniTypeCast;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacTypeCast extends JavacExpression<JCTree.JCTypeCast> implements UniTypeCast {

    public JavacTypeCast(JCTree.@NotNull JCTypeCast tree) {
        super(tree);
    }

    @Override
    public void setType(@NotNull UniElement type) {
        tree.clazz = JavacUtils.javac(type);
    }

    @Override
    public @NotNull UniElement getType() {
        return UniUtils.uni(tree.clazz);
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
