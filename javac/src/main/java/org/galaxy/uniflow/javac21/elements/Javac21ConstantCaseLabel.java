package org.galaxy.uniflow.javac21.elements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.elements.labels.UniConstantCaseLabel;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac21.Reflection;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class Javac21ConstantCaseLabel extends JavacElement<JCTree.JCCaseLabel> implements UniConstantCaseLabel {

    private static final ReflectField EXPRESSION;

    public Javac21ConstantCaseLabel(JCTree.JCCaseLabel tree) {
        super(tree);
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
            ReflectClass type = new ReflectClass(Reflection.CONSTANT_CASE_LABEL_TYPE);
            EXPRESSION = type.field("expr");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
