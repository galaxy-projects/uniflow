package org.galaxy.uniflow.javac21.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.pattern.UniAnyPattern;
import org.galaxy.uniflow.javac15.pattern.JavacPattern;

public class Javac21AnyPattern extends JavacPattern<JCTree.JCPattern> implements UniAnyPattern {

    public Javac21AnyPattern(JCTree.JCPattern tree) {
        super(tree);
    }
}
