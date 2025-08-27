package org.galaxy.uniflow.javac.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.pattern.UniGuardedPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacGuardedPattern extends JavacElement<JCTree.JCGuardPattern> implements UniGuardedPattern {

    public JavacGuardedPattern(JCTree.@NotNull JCGuardPattern tree) {
        super(tree);
    }

    @Override
    public void setPattern(@NotNull UniPattern pattern) {
        tree.patt = JavacUtils.javac(pattern);
    }

    @Override
    public @NotNull UniPattern getPattern() {
        return UniUtils.uni(tree.patt);
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
