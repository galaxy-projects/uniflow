package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniIf;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacIf extends JavacElement<JCTree.JCIf> implements UniIf {

    public JavacIf(JCTree.@NotNull JCIf tree) {
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
    public void setThenStatement(@NotNull UniStatement thenStatement) {
        tree.thenpart = JavacUnwrapper.unwrap(thenStatement);
    }

    @Override
    public @NotNull UniStatement getThenStatement() {
        return UniflowWrapper.wrap(tree.thenpart);
    }

    @Override
    public void setElseStatement(@NotNull UniStatement elseStatement) {
        tree.elsepart = JavacUnwrapper.unwrap(elseStatement);
    }

    @Override
    public @NotNull UniStatement getElseStatement() {
        return UniflowWrapper.wrap(tree.elsepart);
    }
}
