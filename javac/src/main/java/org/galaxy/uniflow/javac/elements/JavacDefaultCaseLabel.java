package org.galaxy.uniflow.javac.elements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.elements.labels.UniDefaultCaseLabel;
import org.galaxy.uniflow.javac.JavacElement;
import org.jetbrains.annotations.NotNull;

public class JavacDefaultCaseLabel extends JavacElement<JCTree.JCDefaultCaseLabel> implements UniDefaultCaseLabel {

    public JavacDefaultCaseLabel(JCTree.@NotNull JCDefaultCaseLabel tree) {
        super(tree);
    }
}
