package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniEnhancedForLoop;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacEnhancedForLoop extends JavacElement<JCTree.JCEnhancedForLoop> implements UniEnhancedForLoop {

    public JavacEnhancedForLoop(JCTree.@NotNull JCEnhancedForLoop tree) {
        super(tree);
    }

    @Override
    public void setVariable(@NotNull UniVariable variable) {
        tree.var = JavacUnwrapper.unwrap(variable);
    }

    @Override
    public @NotNull UniVariable getVariable() {
        return UniflowWrapper.wrap(tree.var);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        tree.expr = JavacUnwrapper.unwrap(expression);
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(tree.expr);
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
