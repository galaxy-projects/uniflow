package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniDoWhileLoop;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacDoWhileLoop extends JavacElement<JCTree.JCDoWhileLoop> implements UniDoWhileLoop {

    public JavacDoWhileLoop(JCTree.@NotNull JCDoWhileLoop tree) {
        super(tree);
    }

    @Override
    public void setCondition(@NotNull UniExpression condition) {
        tree.cond = JavacUnwrapper.unwrap(condition);
    }

    @Override
    public @NotNull UniExpression getCondition() {
        return UniflowWrapper.wrap(tree.cond);
    }

    @Override
    public void setBody(@NotNull UniStatement body) {
        tree.body = JavacUnwrapper.unwrap(body);
    }

    @Override
    public @NotNull UniStatement getBody() {
        return UniflowWrapper.wrap(tree.body);
    }
}
