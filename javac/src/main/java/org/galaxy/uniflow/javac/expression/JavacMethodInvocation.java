package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniMethodInvocation;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacMethodInvocation extends JavacExpression<JCTree.JCMethodInvocation> implements UniMethodInvocation {

    public JavacMethodInvocation(JCTree.@NotNull JCMethodInvocation tree) {
        super(tree);
    }

    @Override
    public @NotNull UniList<@NotNull UniExpression> getTypeArguments() {
        return new JavacList<>(
                () -> tree.typeargs,
                newList -> tree.typeargs = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public void setMethodSelect(@NotNull UniExpression methodSelect) {
        tree.meth = JavacUnwrapper.unwrap(methodSelect);
    }

    @Override
    public @NotNull UniExpression getMethodSelect() {
        return UniflowWrapper.wrap(tree.meth);
    }

    @Override
    public @NotNull UniList<UniExpression> getArguments() {
        return new JavacList<>(
                () -> tree.args,
                newList -> tree.args = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }
}
