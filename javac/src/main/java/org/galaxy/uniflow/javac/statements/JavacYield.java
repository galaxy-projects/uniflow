package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniYield;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacYield extends JavacElement<JCTree.JCYield> implements UniYield {

    public JavacYield(JCTree.@NotNull JCYield tree) {
        super(tree);
    }

    @Override
    public void setValue(@NotNull UniExpression value) {
        tree.value = JavacUtils.javac(value);
    }

    @Override
    public @NotNull UniExpression getValue() {
        return UniUtils.uni(tree.value);
    }
}
