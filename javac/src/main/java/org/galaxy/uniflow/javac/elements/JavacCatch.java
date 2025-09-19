package org.galaxy.uniflow.javac.elements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacCatch extends JavacElement<JCTree.JCCatch> implements UniCatch {

    public JavacCatch(JCTree.@NotNull JCCatch tree) {
        super(tree);
    }

    @Override
    public void setParameter(@NotNull UniVariable parameter) {
        tree.param = JavacUnwrapper.unwrap(parameter);
    }

    @Override
    public @NotNull UniVariable getParameter() {
        return UniflowWrapper.wrap(tree.param);
    }

    @Override
    public void setBody(@NotNull UniBlock body) {
        tree.body = JavacUnwrapper.unwrap(body);
    }

    @Override
    public @NotNull UniBlock getBody() {
        return UniflowWrapper.wrap(tree.body);
    }
}
