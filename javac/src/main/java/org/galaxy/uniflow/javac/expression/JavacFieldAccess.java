package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniFieldAccess;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacFieldAccess extends JavacExpression<JCTree.JCFieldAccess> implements UniFieldAccess {

    public JavacFieldAccess(JCTree.@NotNull JCFieldAccess tree) {
        super(tree);
    }

    @Override
    public @NotNull UniType getSelected() {
        return UniflowWrapper.typeFromTree(tree.selected);
    }

    @Override
    public @NotNull String getName() {
        return NameUtils.nameToString(tree.name);
    }
}
