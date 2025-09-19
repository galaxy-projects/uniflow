package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniIdentifier;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.jetbrains.annotations.NotNull;

public class JavacIdentifier extends JavacExpression<JCTree.JCIdent> implements UniIdentifier {

    public JavacIdentifier(JCTree.@NotNull JCIdent tree) {
        super(tree);
    }

    @Override
    public void setName(@NotNull String name) {
        tree.name = NameUtils.name(name);
    }

    @Override
    public @NotNull String getName() {
        return NameUtils.nameToString(tree.name);
    }
}
