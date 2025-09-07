package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniParameterizedType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacParameterizedType extends JavacType<JCTree.JCTypeApply, Type.ClassType>
        implements UniParameterizedType {

    public JavacParameterizedType(JCTree.JCTypeApply expression, Type.ClassType type) {
        super(expression, type);
    }

    @Override
    public @NotNull UniType getType() {
        return UniflowWrapper.type(type.getEnclosingType());
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getTypeArguments() {
        return new JavacList<>(
                type.getTypeArguments(),
                newList -> type.typarams_field = newList,
                UniflowWrapper::type,
                JavacUnwrapper::unwrap
        );
    }
}
