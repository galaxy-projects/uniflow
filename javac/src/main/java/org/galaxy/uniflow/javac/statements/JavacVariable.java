package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.JavacModifiers;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacVariable extends JavacElement<JCTree.JCVariableDecl> implements UniVariable {

    public JavacVariable(JCTree.@NotNull JCVariableDecl tree) {
        super(tree);
    }

    @Override
    public @NotNull UniModifiers getModifiers() {
        return new JavacModifiers(tree.mods);
    }

    @Override
    public @NotNull String getName() {
        return NameUtils.nameToString(tree.name);
    }

    @Override
    public @NotNull UniType getType() {
        return UniflowWrapper.typeFromTree(tree.vartype);
    }

    @Override
    public void setInitializer(@Nullable UniExpression expression) {
        tree.init = JavacUnwrapper.unwrap(expression);
    }

    @Override
    public @Nullable UniExpression getInitializer() {
        return UniflowWrapper.wrap(tree.init);
    }
}
