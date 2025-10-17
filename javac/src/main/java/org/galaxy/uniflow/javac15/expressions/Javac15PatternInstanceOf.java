package org.galaxy.uniflow.javac15.expressions;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniPatternInstanceOf;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac.expression.JavacInstanceOf;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac15.Reflection;
import org.galaxy.uniflow.javac15.Uniflow15Wrapper;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.Nullable;

public class Javac15PatternInstanceOf extends JavacInstanceOf implements UniPatternInstanceOf {

    private static final ReflectField PATTERN;

    public Javac15PatternInstanceOf(JCTree.JCInstanceOf tree) {
        super(tree);
    }

    @Override
    public void setPattern(@Nullable UniPattern pattern) {
        PATTERN.set(tree, JavacUnwrapper.unwrap(pattern));
    }

    @Override
    public @Nullable UniPattern getPattern() {
        return Uniflow15Wrapper.wrap((JCTree.JCPattern) PATTERN.get(tree));
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.INSTANCEOF_TYPE);
            PATTERN = type.field("pattern");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
