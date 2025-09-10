package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class JavacType<J extends JCTree, T extends Type> implements UniType {

    protected final J expression;
    protected final T type;

    public JavacType(J expression, T type) {
        this.expression = expression;
        this.type = type;
    }

    public @NotNull J getExpression() {
        return Objects.requireNonNull(expression);
    }

    public T getRawType() {
        return type;
    }
}
