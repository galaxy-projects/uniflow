package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniSynchronized;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacSynchronized extends JavacElement<JCTree.JCSynchronized> implements UniSynchronized {

    public JavacSynchronized(JCTree.@NotNull JCSynchronized tree) {
        super(tree);
    }

    @Override
    public void setLock(@NotNull UniExpression lock) {
        tree.lock = JavacUtils.javac(lock);
    }

    @Override
    public @NotNull UniExpression getLock() {
        return UniUtils.uni(tree.lock);
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
