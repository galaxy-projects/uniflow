package org.galaxy.uniflow.javac15.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.pattern.UniBindingPattern;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac15.Reflection;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacBindingPattern extends JavacPattern<JCTree.JCPattern> implements UniBindingPattern {

    private static final ReflectField VARIABLE;

    public JavacBindingPattern(JCTree.JCPattern tree) {
        super(tree);
    }

    @Override
    public void setVariable(@NotNull UniVariable variable) {
        VARIABLE.set(tree, JavacUnwrapper.unwrap(variable));
    }

    @Override
    public @NotNull UniVariable getVariable() {
        return UniflowWrapper.wrap((JCTree.JCVariableDecl) VARIABLE.get(tree));
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.BINDING_PATTERN_TYPE);
            VARIABLE = type.field("var");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
