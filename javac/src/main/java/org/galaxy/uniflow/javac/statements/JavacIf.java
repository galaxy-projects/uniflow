package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniIf;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacIf extends JavacElement<JCTree.JCIf> implements UniIf {

    public JavacIf(JCTree.@NotNull JCIf tree) {
        super(tree);
    }

    @Override
    public void setCondition(@NotNull UniExpression condition) {
        tree.cond = JavacUtils.javac(condition);
    }

    @Override
    public @NotNull UniExpression getCondition() {
        return UniUtils.uni(tree.cond);
    }

    @Override
    public void setThenStatement(@NotNull UniStatement thenStatement) {
        tree.thenpart = JavacUtils.javac(thenStatement);
    }

    @Override
    public @NotNull UniStatement getThenStatement() {
        return UniUtils.uni(tree.thenpart);
    }

    @Override
    public void setElseStatement(@NotNull UniStatement elseStatement) {
        tree.elsepart = JavacUtils.javac(elseStatement);
    }

    @Override
    public @NotNull UniStatement getElseStatement() {
        return UniUtils.uni(tree.elsepart);
    }
}
