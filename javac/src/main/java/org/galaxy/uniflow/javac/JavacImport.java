package org.galaxy.uniflow.javac;

import com.sun.tools.javac.code.Scope;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniImport;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
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
        tree.qualid = JavacUtils.javac(qualifiedElement);
    }

    @Override
    public @NotNull UniElement getQualifiedElement() {
        return UniUtils.uni(tree.qualid);
    }
}
