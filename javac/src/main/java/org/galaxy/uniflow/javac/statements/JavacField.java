package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.statements.UniField;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacField extends JavacVariable implements UniField {

    public JavacField(JCTree.@NotNull JCVariableDecl tree) {
        super(tree);
    }

    @Override
    public @Nullable UniClass getEnclosingClass() {
        if (tree.sym != null && tree.sym.owner instanceof Symbol.ClassSymbol) {
            Symbol.ClassSymbol ownerSymbol = (Symbol.ClassSymbol) tree.sym.owner;
            JCTree.JCClassDecl ownerClass = JavacUniflow.getInstance().trees.getTree(ownerSymbol);

            if (ownerClass == null) return null;
            return new JavacClass(ownerClass);
        }
        throw new IllegalStateException("No owner for field " + NameUtils.nameToString(tree.name));
    }
}
