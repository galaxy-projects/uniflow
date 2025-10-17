package org.galaxy.uniflow.javac21.elements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.elements.labels.UniPatternCaseLabel;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac15.Javac15Unwrapper;
import org.galaxy.uniflow.javac21.Reflection;
import org.galaxy.uniflow.javac21.Uniflow21Wrapper;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class Javac21PatternCaseLabel extends JavacElement<JCTree.JCCaseLabel> implements UniPatternCaseLabel {

    private static final ReflectField PATTERN;

    public Javac21PatternCaseLabel(JCTree.JCCaseLabel tree) {
        super(tree);
    }

    @Override
    public void setPattern(@NotNull UniPattern pattern) {
        PATTERN.set(tree, Javac15Unwrapper.unwrap(pattern));
    }

    @Override
    public @NotNull UniPattern getPattern() {
        return Uniflow21Wrapper.wrap((JCTree.JCPattern) PATTERN.get(tree));
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.PATTERN_CASE_LABEL_TYPE);
            PATTERN = type.field("pat");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
