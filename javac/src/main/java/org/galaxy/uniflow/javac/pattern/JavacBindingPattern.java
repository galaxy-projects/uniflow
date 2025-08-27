package org.galaxy.uniflow.javac.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.pattern.UniBindingPattern;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacBindingPattern extends JavacElement<JCTree.JCBindingPattern> implements UniBindingPattern {

    public JavacBindingPattern(JCTree.@NotNull JCBindingPattern tree) {
        super(tree);
    }

    @Override
    public void setVariable(@NotNull UniVariable variable) {
        tree.var = JavacUtils.javac(variable);
    }

    @Override
    public @NotNull UniVariable getVariable() {
        return UniUtils.uni(tree.var);
    }
}
