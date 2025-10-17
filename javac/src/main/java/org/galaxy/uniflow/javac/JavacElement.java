package org.galaxy.uniflow.javac;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.common.EnumUtils;
import org.jetbrains.annotations.NotNull;

public class JavacElement<T extends JCTree> implements UniElement {

    protected final @NotNull T tree;

    public JavacElement(@NotNull T tree) {
        this.tree = tree;
    }

    public @NotNull T getTree() {
        return tree;
    }

    @Override
    public int getPosition() {
        return tree.pos;
    }

    @Override
    public @NotNull Kind getKind() {
        return EnumUtils.convert(Kind.class, tree.getKind());
    }
}
