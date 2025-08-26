package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniArrayAccess;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacArrayAccess extends JavacExpression<JCTree.JCArrayAccess> implements UniArrayAccess {

    public JavacArrayAccess(JCTree.@NotNull JCArrayAccess tree) {
        super(tree);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        tree.indexed = JavacUtils.javac(expression);
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniUtils.uni(tree.indexed);
    }

    @Override
    public void setIndex(@NotNull UniExpression index) {
        tree.index = JavacUtils.javac(index);
    }

    @Override
    public @NotNull UniExpression getIndex() {
        return UniUtils.uni(tree.index);
    }
}
