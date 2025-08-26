package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniNewClass;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO: update sub values (def, constructor)
public class JavacNewClass extends JavacExpression<JCTree.JCNewClass> implements UniNewClass {

    public JavacNewClass(JCTree.@NotNull JCNewClass tree) {
        super(tree);
    }

    @Override
    public void setEnclosingExpression(@Nullable UniExpression enclosingExpression) {
        tree.encl = JavacUtils.javac(enclosingExpression);
    }

    @Override
    public @Nullable UniExpression getEnclosingExpression() {
        return UniUtils.uni(tree.encl);
    }

    @Override
    public @NotNull UniList<@NotNull UniExpression> getTypeArguments() {
        return new JavacList<>(
                tree.typeargs,
                newList -> tree.typeargs = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public void setIdentifier(@NotNull UniExpression identifier) {
        tree.clazz = JavacUtils.javac(identifier);
    }

    @Override
    public @NotNull UniExpression getIdentifier() {
        return UniUtils.uni(tree.clazz);
    }

    @Override
    public @NotNull UniList<@NotNull UniExpression> getArguments() {
        return new JavacList<>(
                tree.args,
                newList -> tree.args = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public @NotNull UniClassType getClassName() {
        return (UniClassType) UniUtils.typeFromTree(tree.clazz);
    }
}
