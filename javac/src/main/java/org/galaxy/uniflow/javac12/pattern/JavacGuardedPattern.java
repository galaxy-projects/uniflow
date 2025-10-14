package org.galaxy.uniflow.javac12.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.pattern.UniGuardedPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac12.Javac12Unwrapper;
import org.galaxy.uniflow.javac12.Reflection;
import org.galaxy.uniflow.javac12.Uniflow12Wrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class JavacGuardedPattern extends JavacPattern<JCTree.JCPattern> implements UniGuardedPattern {

    private static final Field PATTERN;
    private static final Field EXPRESSION;

    public JavacGuardedPattern(JCTree.@NotNull JCPattern tree) {
        super(tree);
    }

    @Override
    public void setPattern(@NotNull UniPattern pattern) {
        try {
            PATTERN.set(tree, Javac12Unwrapper.unwrap(pattern));
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public @NotNull UniPattern getPattern() {
        try {
            return Uniflow12Wrapper.wrap((JCTree.JCPattern) PATTERN.get(tree));
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        try {
            EXPRESSION.set(tree, JavacUnwrapper.unwrap(expression));
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public @NotNull UniExpression getExpression() {
        try {
            return UniflowWrapper.wrap((JCTree.JCExpression) EXPRESSION.get(tree));
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    static {
        try {
            Class<?> type = Reflection.GUARD_PATTERN_TYPE;
            PATTERN = type.getDeclaredField("patt");
            EXPRESSION = type.getDeclaredField("expr");

            PATTERN.setAccessible(true);
            EXPRESSION.setAccessible(true);
        } catch (Throwable e) {
            throw new UnsupportedOperationException("Not supported in this java version");
        }
    }
}
