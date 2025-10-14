package org.galaxy.uniflow.javac12;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.jetbrains.annotations.NotNull;

public class Javac15Unwrapper {

    public static @NotNull JCTree.JCPattern unwrap(UniPattern pattern) {
        return (JCTree.JCPattern) JavacUnwrapper.unwrap((UniElement) pattern);
    }
}
