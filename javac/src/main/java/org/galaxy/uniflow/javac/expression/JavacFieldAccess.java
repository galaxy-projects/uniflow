package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniFieldAccess;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.types.JavacExpressionType;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacFieldAccess extends JavacExpression<JCTree.JCFieldAccess> implements UniFieldAccess {

    public JavacFieldAccess(JCTree.@NotNull JCFieldAccess tree) {
        super(tree);
    }

    @Override
    public void setSelected(@NotNull UniType selected) {
        if (!(selected instanceof JavacExpressionType<?, ?>))
            throw new IllegalArgumentException("Selected type must be JavacExpressionType");
        JavacExpressionType<?, ?> type = (JavacExpressionType<?, ?>) selected;

        tree.selected = type.getExpression();
    }

    @Override
    public @NotNull UniType getSelected() {
        return UniflowWrapper.typeFromTree(tree.selected);
    }

    @Override
    public void setName(@NotNull String name) {
        tree.name = NameUtils.name(name);
    }

    @Override
    public @NotNull String getName() {
        return NameUtils.nameToString(tree.name);
    }
}
