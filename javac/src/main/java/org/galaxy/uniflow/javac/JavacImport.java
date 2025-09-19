package org.galaxy.uniflow.javac;

import com.sun.tools.javac.code.Scope;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniImport;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacImport extends JavacElement<JCTree.JCImport> implements UniImport {

    public JavacImport(JCTree.@NotNull JCImport tree) {
        super(tree);
    }

    @Override
    public boolean isGroup() {
        return tree.importScope instanceof Scope.StarImportScope;
    }

    @Override
    public boolean isStatic() {
        return tree.isStatic();
    }

    @Override
    public void setQualifiedElement(@NotNull UniElement qualifiedElement) {
        tree.qualid = JavacUnwrapper.unwrap(qualifiedElement);
    }

    @Override
    public @NotNull UniElement getQualifiedElement() {
        return UniflowWrapper.wrap(tree.qualid);
    }
}
