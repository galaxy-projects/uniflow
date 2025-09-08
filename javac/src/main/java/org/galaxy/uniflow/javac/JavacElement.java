package org.galaxy.uniflow.javac;

import com.sun.tools.javac.tree.JCTree;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.common.EnumUtils;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class JavacElement<T extends JCTree> implements UniElement {

    @Getter
    protected final @NotNull T tree;

    @Override
    public int getPosition() {
        return tree.pos;
    }

    @Override
    public @NotNull Kind getKind() {
        return EnumUtils.convert(Kind.class, tree.getKind());
    }

    @Override
    public boolean hasTag(@NotNull Tag tag) {
        return getTag() == tag;
    }

    @Override
    public Tag getTag() {
        if (tree instanceof JCTree)
            return EnumUtils.convert(Tag.class, ((JCTree) tree).getTag());
        throw new UnsupportedOperationException("getTag() with value: " + tree);
    }
}
