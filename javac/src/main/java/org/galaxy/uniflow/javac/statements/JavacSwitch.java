package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniSwitch;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacSwitch extends JavacElement<JCTree.JCSwitch> implements UniSwitch {

    public JavacSwitch(JCTree.@NotNull JCSwitch tree) {
        super(tree);
    }

    @Override
    public void setSelector(@NotNull UniExpression selector) {
        tree.selector = JavacUtils.javac(selector);
    }

    @Override
    public @NotNull UniExpression getSelector() {
        return UniUtils.uni(tree.selector);
    }

    @Override
    public @NotNull UniList<UniCase> getCases() {
        return new JavacList<>(
                tree.cases,
                newList -> tree.cases = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }
}
