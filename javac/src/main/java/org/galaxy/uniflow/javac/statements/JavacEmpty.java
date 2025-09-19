package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.statements.UniEmpty;
import org.galaxy.uniflow.javac.JavacElement;
import org.jetbrains.annotations.NotNull;

public class JavacEmpty extends JavacElement<JCTree.JCSkip> implements UniEmpty {

    public JavacEmpty(JCTree.@NotNull JCSkip tree) {
        super(tree);
    }
}
