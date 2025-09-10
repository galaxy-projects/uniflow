package org.galaxy.uniflow.javac.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.pattern.UniGuardedPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacGuardedPattern extends JavacPattern<JCTree.JCGuardPattern> implements UniGuardedPattern {

    public JavacGuardedPattern(JCTree.@NotNull JCGuardPattern tree) {
        super(tree);
    }

    @Override
    public void setPattern(@NotNull UniPattern pattern) {
        tree.patt = JavacUnwrapper.unwrap(pattern);
    }

    @Override
    public @NotNull UniPattern getPattern() {
        return UniflowWrapper.wrap(tree.patt);
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
