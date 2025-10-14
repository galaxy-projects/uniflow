package org.galaxy.uniflow.javac12;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac12.expression.JavacSwitchExpression;
import org.galaxy.uniflow.javac12.pattern.JavacBindingPattern;
import org.galaxy.uniflow.javac12.pattern.JavacGuardedPattern;
import org.galaxy.uniflow.javac12.pattern.JavacParenthesizedPattern;
import org.galaxy.uniflow.javac9.Uniflow9Wrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Uniflow12Wrapper extends Uniflow9Wrapper {

    public static final Uniflow12Wrapper INSTANCE = new Uniflow12Wrapper();

    @Override
    public @Nullable UniElement wrap(JCTree element) {
        if (element instanceof JCTree.JCPattern)
            return wrap((JCTree.JCPattern) element);
        else if (element instanceof JCTree.JCSwitchExpression)
            return new JavacSwitchExpression((JCTree.JCSwitchExpression) element);
        return super.wrap(element);
    }

    public static @NotNull UniPattern wrap(JCTree.JCPattern pattern) {
        if (pattern instanceof JCTree.JCBindingPattern)
            return new JavacBindingPattern((JCTree.JCBindingPattern) pattern);
        else if (Reflection.GUARD_PATTERN_TYPE.isInstance(pattern))
            return new JavacGuardedPattern(pattern);
        else if (Reflection.PARENTHESIZED_PATTERN_TYPE.isInstance(pattern))
            return new JavacParenthesizedPattern(pattern);
        throw new IllegalArgumentException("Unknown pattern: " + pattern);
    }
}
