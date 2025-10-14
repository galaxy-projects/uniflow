package org.galaxy.uniflow.javac15.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac.JavacElement;
import org.jetbrains.annotations.NotNull;

public class JavacPattern<T extends JCTree.JCPattern> extends JavacElement<T> implements UniPattern {

    public JavacPattern(@NotNull T tree) {
        super(tree);
    }
}
