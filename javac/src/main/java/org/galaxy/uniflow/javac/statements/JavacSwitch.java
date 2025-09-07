package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniSwitch;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacSwitch extends JavacElement<JCTree.JCSwitch> implements UniSwitch {

    public JavacSwitch(JCTree.@NotNull JCSwitch tree) {
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
                tree.cases,
                newList -> tree.cases = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }
}
