package org.galaxy.uniflow.javac15.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.pattern.UniParenthesizedPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac15.Javac15Unwrapper;
import org.galaxy.uniflow.javac15.Reflection;
import org.galaxy.uniflow.javac15.Uniflow15Wrapper;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacParenthesizedPattern extends JavacPattern<JCTree.JCPattern>
        implements UniParenthesizedPattern {

    private static final ReflectField PATTERN;

    public JavacParenthesizedPattern(@NotNull JCTree.JCPattern tree) {
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

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.PARENTHESIZED_PATTERN_TYPE);
            PATTERN = type.field("pattern");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException("Not supported in this java version");
        }
    }
}
