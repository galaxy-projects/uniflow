package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniAssert;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacAssert extends JavacElement<JCTree.JCAssert> implements UniAssert {

    public JavacAssert(JCTree.@NotNull JCAssert tree) {
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
    public void setDetails(@Nullable UniExpression details) {
        tree.detail = JavacUnwrapper.unwrap(details);
    }

    @Override
    public @Nullable UniExpression getDetails() {
        return UniflowWrapper.wrap(tree.detail);
    }
}
