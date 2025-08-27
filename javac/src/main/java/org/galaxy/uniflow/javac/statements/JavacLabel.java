package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.statements.UniLabel;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacLabel extends JavacElement<JCTree.JCLabeledStatement> implements UniLabel {

    public JavacLabel(JCTree.@NotNull JCLabeledStatement tree) {
        super(tree);
    }

    @Override
    public void setLabel(@NotNull String label) {
        tree.label = NameUtils.name(label);
    }

    @Override
    public @NotNull String getLabel() {
        return NameUtils.nameToString(tree.label);
    }

    @Override
    public void setBody(@NotNull UniStatement body) {
        tree.body = JavacUtils.javac(body);
    }

    @Override
    public @NotNull UniStatement getBody() {
        return UniUtils.uni(tree.body);
    }
}
