package org.galaxy.uniflow.javac12.pattern;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.pattern.UniBindingPattern;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacBindingPattern extends JavacPattern<JCTree.JCBindingPattern> implements UniBindingPattern {

    public JavacBindingPattern(JCTree.@NotNull JCBindingPattern tree) {
        super(tree);
    }

    @Override
    public void setVariable(@NotNull UniVariable variable) {
        tree.var = JavacUnwrapper.unwrap(variable);
    }

    @Override
    public @NotNull UniVariable getVariable() {
        return UniflowWrapper.wrap(tree.var);
    }
}
