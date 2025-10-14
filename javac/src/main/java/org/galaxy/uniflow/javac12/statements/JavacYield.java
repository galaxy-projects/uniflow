package org.galaxy.uniflow.javac12.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniYield;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacYield extends JavacElement<JCTree.JCYield> implements UniYield {

    public JavacYield(JCTree.@NotNull JCYield tree) {
        super(tree);
    }

    @Override
    public void setValue(@NotNull UniExpression value) {
        tree.value = JavacUnwrapper.unwrap(value);
    }

    @Override
    public @NotNull UniExpression getValue() {
        return UniflowWrapper.wrap(tree.value);
    }
}
