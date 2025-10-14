package org.galaxy.uniflow.javac12;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.javac12.expression.JavacSwitchExpression;
import org.galaxy.uniflow.javac9.Uniflow9Wrapper;
import org.jetbrains.annotations.Nullable;

public class Uniflow12Wrapper extends Uniflow9Wrapper {

    public static final Uniflow12Wrapper INSTANCE = new Uniflow12Wrapper();

    @Override
    public @Nullable UniElement wrap(JCTree element) {
        if (element instanceof JCTree.JCSwitchExpression)
            return new JavacSwitchExpression((JCTree.JCSwitchExpression) element);
        return super.wrap(element);
    }
}
