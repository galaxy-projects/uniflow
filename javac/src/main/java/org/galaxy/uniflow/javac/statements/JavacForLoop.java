package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniExpressionStatement;
import org.galaxy.uniflow.api.statements.UniForLoop;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacForLoop extends JavacElement<JCTree.JCForLoop> implements UniForLoop {

    public JavacForLoop(JCTree.@NotNull JCForLoop tree) {
        super(tree);
    }

    @Override
    public @NotNull UniList<UniStatement> getInitializer() {
        return new JavacList<>(
                tree.init,
                newList -> tree.init = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
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
    public @NotNull UniList<@NotNull UniExpressionStatement> getUpdate() {
        return new JavacList<>(
                tree.step,
                newList -> tree.step = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
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
