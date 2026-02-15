package org.galaxy.uniflow.javac;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.javac.statements.JavacBlock;
import org.jetbrains.annotations.NotNull;

public class JavacClassInitializer extends JavacBlock implements UniClassInitializer {

    public JavacClassInitializer(JCTree.@NotNull JCBlock tree) {
        super(tree);
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.CLASS_INITIALIZER;
    }
}
