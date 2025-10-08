package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.statements.UniParameter;
import org.jetbrains.annotations.NotNull;

public class JavacParameter extends JavacVariable implements UniParameter {

    public JavacParameter(JCTree.@NotNull JCVariableDecl tree) {
        super(tree);
    }
}
