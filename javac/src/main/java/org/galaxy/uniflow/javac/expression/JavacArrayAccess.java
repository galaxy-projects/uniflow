package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniArrayAccess;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacArrayAccess extends JavacExpression<JCTree.JCArrayAccess> implements UniArrayAccess {

    public JavacArrayAccess(JCTree.@NotNull JCArrayAccess tree) {
        super(tree);
    }

    @Override
    public void set(@NotNull UniExpression array, @NotNull UniExpression index) {
        setArray(array);
        setIndex(index);
    }

    @Override
    public void setArray(@NotNull UniExpression array) {
        tree.indexed = JavacUnwrapper.unwrap(array);
    }

    @Override
    public @NotNull UniExpression getArray() {
        return UniflowWrapper.wrap(tree.indexed);
    }

    @Override
    public void setIndex(@NotNull UniExpression index) {
        tree.index = JavacUnwrapper.unwrap(index);
    }

    @Override
    public @NotNull UniExpression getIndex() {
        return UniflowWrapper.wrap(tree.index);
    }
}
