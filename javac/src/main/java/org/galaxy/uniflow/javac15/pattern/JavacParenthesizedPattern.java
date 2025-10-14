package org.galaxy.uniflow.javac15.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.pattern.UniParenthesizedPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac12.Uniflow12Wrapper;
import org.galaxy.uniflow.javac15.Javac15Unwrapper;
import org.galaxy.uniflow.javac15.Reflection;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class JavacParenthesizedPattern extends JavacPattern<JCTree.JCPattern>
        implements UniParenthesizedPattern {

    private static final Field PATTERN;

    public JavacParenthesizedPattern(@NotNull JCTree.JCPattern tree) {
        super(tree);
    }

    @Override
    public void setPattern(@NotNull UniPattern pattern) {
        try {
            PATTERN.set(tree, Javac15Unwrapper.unwrap(pattern));
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

    static {
        try {
            PATTERN = Reflection.PARENTHESIZED_PATTERN_TYPE.getDeclaredField("pattern");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException("Not supported in this java version");
        }
    }
}
