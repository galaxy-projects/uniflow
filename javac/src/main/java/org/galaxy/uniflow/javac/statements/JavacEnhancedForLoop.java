package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniEnhancedForLoop;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacEnhancedForLoop extends JavacElement<JCTree.JCEnhancedForLoop> implements UniEnhancedForLoop {

    public JavacEnhancedForLoop(JCTree.@NotNull JCEnhancedForLoop tree) {
        super(tree);
    }

    @Override
    public void setVariable(@NotNull UniVariable variable) {
        tree.var = JavacUtils.javac(variable);
    }

    @Override
    public @NotNull UniVariable getVariable() {
        return UniUtils.uni(tree.var);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        tree.expr = JavacUtils.javac(expression);
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniUtils.uni(tree.expr);
    }

    @Override
    public void setBody(@NotNull UniStatement body) {
        tree.body = JavacUtils.javac(body);
    }

    @Override
    public @NotNull UniStatement getBody() {
        return UniUtils.uni(tree.body);
    }
}
