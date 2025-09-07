package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.JavacElement;
import org.jetbrains.annotations.NotNull;

public class JavacStatement<T extends JCTree.JCStatement> extends JavacElement<T> implements UniStatement {

    public JavacStatement(@NotNull T tree) {
        super(tree);
    }
}
