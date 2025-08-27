package org.galaxy.uniflow.javac.elements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.javac.JavacElement;
import org.jetbrains.annotations.NotNull;

public class JavacCaseLabel extends JavacElement<JCTree.JCCaseLabel> implements UniCaseLabel {

    public JavacCaseLabel(JCTree.@NotNull JCCaseLabel tree) {
        super(tree);
    }
}
