package org.galaxy.uniflow.javac.elements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacCatch extends JavacElement<JCTree.JCCatch> implements UniCatch {

    public JavacCatch(JCTree.@NotNull JCCatch tree) {
        super(tree);
    }

    @Override
    public void setParameter(@NotNull UniVariable parameter) {
        tree.param = JavacUtils.javac(parameter);
    }

    @Override
    public @NotNull UniVariable getParameter() {
        return UniUtils.uni(tree.param);
    }

    @Override
    public void setBody(@NotNull UniBlock body) {
        tree.body = JavacUtils.javac(body);
    }

    @Override
    public @NotNull UniBlock getBody() {
        return UniUtils.uni(tree.body);
    }
}
