package org.galaxy.uniflow.javac12.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.statements.UniYield;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac12.Reflection;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacYield extends JavacElement<JCTree.JCStatement> implements UniYield {

    private static final ReflectField VALUE;

    public JavacYield(JCTree.JCStatement tree) {
        super(tree);
    }

    @Override
    public void setValue(@NotNull UniExpression value) {
        VALUE.set(tree, JavacUnwrapper.unwrap(value));
    }

    @Override
    public @NotNull UniExpression getValue() {
        return UniflowWrapper.wrap((JCTree.JCExpression) VALUE.get(tree));
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.YIELD_TYPE);
            VALUE = type.field("value");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
