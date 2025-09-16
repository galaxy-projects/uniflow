package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniNewClass;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacNewClass extends JavacExpression<JCTree.JCNewClass> implements UniNewClass {

    public JavacNewClass(JCTree.@NotNull JCNewClass tree) {
        super(tree);
    }

    @Override
    public @Nullable UniExpression getEnclosingExpression() {
        return UniflowWrapper.wrap(tree.encl);
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
    public @NotNull UniExpression getIdentifier() {
        return UniflowWrapper.wrap(tree.clazz);
    }

    @Override
    public @NotNull UniList<@NotNull UniExpression> getArguments() {
        return new JavacList<>(
                () -> tree.args,
                newList -> tree.args = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniClassType getClassName() {
        return (UniClassType) UniflowWrapper.typeFromTree(tree.clazz);
    }
}
