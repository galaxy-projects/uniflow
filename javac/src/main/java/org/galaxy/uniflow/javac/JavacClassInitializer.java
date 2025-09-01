package org.galaxy.uniflow.javac;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacClassInitializer extends JavacElement<JCTree.JCBlock> implements UniClassInitializer {

    public JavacClassInitializer(JCTree.@NotNull JCBlock tree) {
        super(tree);
    }

    @Override
    public void setStatic(boolean isStatic) {
        tree.flags |= Flags.STATIC;
    }

    @Override
    public boolean isStatic() {
        return (tree.flags & Flags.STATIC) != 0;
    }

    @Override
    public @NotNull UniList<UniStatement> getStatements() {
        return new JavacList<>(
                tree.stats,
                newList -> tree.stats = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }
}
