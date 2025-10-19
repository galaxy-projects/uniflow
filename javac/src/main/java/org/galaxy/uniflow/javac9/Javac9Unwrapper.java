package org.galaxy.uniflow.javac9;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.jetbrains.annotations.NotNull;

public class Javac9Unwrapper {

    public static @NotNull JCTree.JCDirective unwrap(UniDirective directive) {
        return (JCTree.JCDirective) JavacUnwrapper.unwrap(directive);
    }
}
