package org.galaxy.uniflow.javac12.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniSwitchExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.statements.UniJdk12Case;
import org.galaxy.uniflow.javac.expression.JavacExpression;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac12.Reflection;
import org.galaxy.uniflow.javac12.Uniflow12Wrapper;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacSwitchExpression extends JavacExpression<JCTree.JCExpression> implements UniSwitchExpression {

    private static final ReflectField SELECTOR;
    private static final ReflectField CASES;

    public JavacSwitchExpression(JCTree.JCExpression tree) {
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
    public @NotNull UniList<@NotNull UniJdk12Case> getCases() {
        return new JavacList<UniJdk12Case, JCTree.JCCase>(
                CASES.createGetter(tree),
                CASES.createSetter(tree),
                Uniflow12Wrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.SWITCH_EXPRESSION_TYPE);
            SELECTOR = type.field("selector");
            CASES = type.field("cases");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
