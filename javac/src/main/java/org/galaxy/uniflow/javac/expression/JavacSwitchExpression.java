package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniSwitchExpression;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacSwitchExpression extends JavacExpression<JCTree.JCSwitchExpression> implements UniSwitchExpression {

    public JavacSwitchExpression(JCTree.@NotNull JCSwitchExpression tree) {
        super(tree);
    }

    @Override
    public void setSelector(@NotNull UniExpression selector) {
        tree.selector = JavacUnwrapper.unwrap(selector);
    }

    @Override
    public @NotNull UniExpression getSelector() {
        return UniflowWrapper.wrap(tree.selector);
    }

    @Override
    public @NotNull UniList<UniCase> getCases() {
        return new JavacList<>(
                () -> tree.cases,
                newList -> tree.cases = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }
}
