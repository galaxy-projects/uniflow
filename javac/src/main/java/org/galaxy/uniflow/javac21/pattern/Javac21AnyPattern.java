package org.galaxy.uniflow.javac21.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.pattern.UniAnyPattern;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac15.pattern.JavacPattern;
import org.jetbrains.annotations.NotNull;

public class Javac21AnyPattern extends JavacPattern<JCTree.JCPattern> implements UniAnyPattern {

    public Javac21AnyPattern(JCTree.JCPattern tree) {
        super(tree);
    }

    @Override
    public void setType(@NotNull UniType type) {}

    @Override
    public @NotNull UniType getType() {
        throw new UnsupportedOperationException();
    }
}
