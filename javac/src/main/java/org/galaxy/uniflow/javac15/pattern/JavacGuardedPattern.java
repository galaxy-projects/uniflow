package org.galaxy.uniflow.javac15.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.pattern.UniGuardedPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac15.Javac15Unwrapper;
import org.galaxy.uniflow.javac15.Reflection;
import org.galaxy.uniflow.javac15.Uniflow15Wrapper;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacGuardedPattern extends JavacPattern<JCTree.JCPattern> implements UniGuardedPattern {

    private static final ReflectField PATTERN;
    private static final ReflectField EXPRESSION;

    public JavacGuardedPattern(JCTree.JCPattern tree) {
        super(tree);
    }

    @Override
    public void setPattern(@NotNull UniPattern pattern) {
        PATTERN.set(tree, Javac15Unwrapper.unwrap(pattern));
    }

    @Override
    public @NotNull UniPattern getPattern() {
        return Uniflow15Wrapper.wrap((JCTree.JCPattern) PATTERN.get(tree));
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        EXPRESSION.set(tree, JavacUnwrapper.unwrap(expression));
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap((JCTree.JCExpression) EXPRESSION.get(tree));
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.GUARD_PATTERN_TYPE);
            PATTERN = type.field("patt");
            EXPRESSION = type.field("expr");
        } catch (Throwable e) {
            throw new UnsupportedOperationException("Not supported in this java version");
        }
    }
}
