package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniInstanceOf;
import org.galaxy.uniflow.api.expressions.pattern.UniPattern;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacInstanceOf extends JavacExpression<JCTree.JCInstanceOf> implements UniInstanceOf {

    public JavacInstanceOf(JCTree.@NotNull JCInstanceOf tree) {
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

    @Override
    public @NotNull UniElement getType() {
        return UniUtils.uni(tree.getType());
    }

    @Override
    public void setPattern(@Nullable UniPattern pattern) {
        tree.pattern = JavacUtils.javac(pattern);
    }

    @Override
    public @Nullable UniPattern getPattern() {
        return (UniPattern) UniUtils.uni(tree.pattern);
    }
}
