package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacBlock extends JavacElement<JCTree.JCBlock> implements UniBlock {

    public JavacBlock(JCTree.@NotNull JCBlock tree) {
        super(tree);
    }

    @Override
    public void setStatic(boolean isStatic) {
        tree.flags |= Flags.STATIC;
    }

    @Override
    public boolean isStatic() {
        return tree.isStatic();
    }

    @Override
    public @NotNull UniList<@NotNull UniStatement> getStatements() {
        return new JavacList<>(
                () -> tree.stats,
                newList -> tree.stats = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }
}
