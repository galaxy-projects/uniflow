package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacClassType extends JavacExpressionType<JCTree.JCExpression, Type.ClassType> implements UniClassType {

    public JavacClassType(JCTree.JCIdent expression, Type.ClassType type) {
        super(expression, type);
    }

    public JavacClassType(JCTree.JCFieldAccess expression, Type.ClassType type) {
        super(expression, type);
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getParameterTypes() {
        return new JavacList<>(
                type.typarams_field,
                newList -> type.typarams_field = newList,
                UniflowWrapper::type,
                JavacUnwrapper::unwrap
        );
    }
}
