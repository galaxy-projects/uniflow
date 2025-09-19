package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.statements.UniContinue;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacContinue extends JavacElement<JCTree.JCContinue> implements UniContinue {

    public JavacContinue(JCTree.@NotNull JCContinue tree) {
        super(tree);
    }

    @Override
    public void setLabel(@Nullable String label) {
        tree.label = NameUtils.name(label);
    }

    @Override
    public @Nullable String getLabel() {
        return NameUtils.nameToString(tree.label);
    }
}
