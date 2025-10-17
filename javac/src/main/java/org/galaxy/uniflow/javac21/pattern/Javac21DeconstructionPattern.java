package org.galaxy.uniflow.javac21.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.pattern.UniDeconstructionPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac15.Javac15Unwrapper;
import org.galaxy.uniflow.javac15.pattern.JavacPattern;
import org.galaxy.uniflow.javac21.Reflection;
import org.galaxy.uniflow.javac21.Uniflow21Wrapper;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class Javac21DeconstructionPattern extends JavacPattern<JCTree.JCPattern>
        implements UniDeconstructionPattern {

    private static final ReflectField DECONSTRUCTOR;
    private static final ReflectField NESTED;

    public Javac21DeconstructionPattern(JCTree.JCPattern tree) {
        super(tree);
    }

    @Override
    public void setDeconstructor(@NotNull UniExpression deconstructor) {
        DECONSTRUCTOR.set(tree, JavacUnwrapper.unwrap(deconstructor));
    }

    @Override
    public @NotNull UniExpression getDeconstructor() {
        return UniflowWrapper.wrap((JCTree.JCExpression) DECONSTRUCTOR.get(tree));
    }

    @Override
    public @NotNull UniList<@NotNull UniPattern> getNestedPatterns() {
        return new JavacList<>(
                NESTED.createGetter(tree),
                NESTED.createSetter(tree),
                Uniflow21Wrapper::wrap,
                Javac15Unwrapper::unwrap
        );
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.RECORD_PATTERN_TYPE);
            DECONSTRUCTOR = type.field("deconstructor");
            NESTED = type.field("nested");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
