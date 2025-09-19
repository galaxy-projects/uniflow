package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.types.UniArrayType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacArrayType extends JavacExpressionType<JCTree.JCArrayTypeTree, Type.ArrayType>
        implements UniArrayType {

    public JavacArrayType(JCTree.JCArrayTypeTree expression, Type.ArrayType type) {
        super(expression, type);
    }

    @Override
    public void setType(@NotNull UniType type) {
        this.type.elemtype = JavacUnwrapper.unwrap(type);
    }

    @Override
    public @NotNull UniType getType() {
        return UniflowWrapper.type(type.elemtype);
    }
}
