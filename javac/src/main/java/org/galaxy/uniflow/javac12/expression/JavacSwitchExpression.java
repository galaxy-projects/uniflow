package org.galaxy.uniflow.javac12.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniSwitchExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.javac.expression.JavacExpression;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac12.Reflection;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacSwitchExpression extends JavacExpression<JCTree.JCSwitchExpression> implements UniSwitchExpression {

    private static final ReflectField SELECTOR;
    private static final ReflectField CASES;

    public JavacSwitchExpression(JCTree.@NotNull JCSwitchExpression tree) {
        super(tree);
    }

    @Override
    public void setSelector(@NotNull UniExpression selector) {
        SELECTOR.set(tree, JavacUnwrapper.unwrap(selector));
    }

    @Override
    public @NotNull UniExpression getSelector() {
        return UniflowWrapper.wrap((JCTree.JCExpression) SELECTOR.get(tree));
    }

    @Override
    public @NotNull UniList<UniCase> getCases() {
        return new JavacList<UniCase, JCTree.JCCase>(
                CASES.createGetter(tree),
                CASES.createSetter(tree),
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.SWITCH_EXPRESSION);
            SELECTOR = type.field("selector");
            CASES = type.field("cases");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
