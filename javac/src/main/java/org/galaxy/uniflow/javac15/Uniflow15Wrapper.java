package org.galaxy.uniflow.javac15;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac12.Uniflow12Wrapper;
import org.galaxy.uniflow.javac15.pattern.JavacBindingPattern;
import org.galaxy.uniflow.javac15.pattern.JavacGuardedPattern;
import org.galaxy.uniflow.javac15.pattern.JavacParenthesizedPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Uniflow15Wrapper extends Uniflow12Wrapper {

    @Override
    public @Nullable UniElement wrap(JCTree element) {
        if (element instanceof JCTree.JCPattern)
            return wrap((JCTree.JCPattern) element);
        return super.wrap(element);
    }

    public static @NotNull UniPattern wrap(JCTree.JCPattern pattern) {
        if (pattern instanceof JCTree.JCBindingPattern)
            return new JavacBindingPattern(pattern);
        else if (Reflection.GUARD_PATTERN_TYPE.isInstance(pattern))
            return new JavacGuardedPattern(pattern);
        else if (Reflection.PARENTHESIZED_PATTERN_TYPE.isInstance(pattern))
            return new JavacParenthesizedPattern(pattern);
        throw new IllegalArgumentException("Unknown pattern: " + pattern);
    }
}
