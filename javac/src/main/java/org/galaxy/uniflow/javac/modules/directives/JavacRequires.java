package org.galaxy.uniflow.javac.modules.directives;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniRequires;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacRequires extends JavacElement<JCTree.JCRequires> implements UniRequires {

    public JavacRequires(JCTree.@NotNull JCRequires tree) {
        super(tree);
    }

    @Override
    public void setStatic(boolean isStatic) {
        tree.isStaticPhase = isStatic;
    }

    @Override
    public boolean isStatic() {
        return tree.isStaticPhase;
    }

    @Override
    public void setTransitive(boolean transitive) {
        tree.isTransitive = transitive;
    }

    @Override
    public boolean isTransitive() {
        return tree.isTransitive;
    }

    @Override
    public void setModuleName(@NotNull UniExpression moduleName) {
        tree.moduleName = JavacUnwrapper.unwrap(moduleName);
    }

    @Override
    public @NotNull UniExpression getModuleName() {
        return UniflowWrapper.wrap(tree.moduleName);
    }
}
