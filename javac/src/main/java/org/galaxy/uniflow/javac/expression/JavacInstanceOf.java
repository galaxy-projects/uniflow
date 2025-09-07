package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniInstanceOf;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacInstanceOf extends JavacExpression<JCTree.JCInstanceOf> implements UniInstanceOf {

    public JavacInstanceOf(JCTree.@NotNull JCInstanceOf tree) {
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

    @Override
    public @NotNull UniElement getType() {
        return UniflowWrapper.wrap(tree.getType());
    }

    @Override
    public void setPattern(@Nullable UniPattern pattern) {
        tree.pattern = JavacUnwrapper.unwrap(pattern);
    }

    @Override
    public @Nullable UniPattern getPattern() {
        return (UniPattern) UniflowWrapper.wrap(tree.pattern);
    }
}
