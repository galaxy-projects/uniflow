package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniIntersectionType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JavacIntersectionType extends JavacExpressionType<JCTree.JCTypeIntersection, Type.IntersectionClassType>
        implements UniIntersectionType {

    public JavacIntersectionType(JCTree.JCTypeIntersection expression, Type.IntersectionClassType type) {
        super(expression, type);
    }

    @Override
    public @NotNull List<@NotNull UniType> getComponents() {
        return new ArrayList<>(type.getComponents().map(UniflowWrapper::type));
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getInterfaces() {
        return new JavacList<>(
                () -> type.interfaces_field,
                newList -> type.interfaces_field = newList,
                UniflowWrapper::type,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public void setSupertype(@NotNull UniType supertype) {
        type.supertype_field = JavacUnwrapper.unwrap(supertype);
    }

    @Override
    public @NotNull UniType getSupertype() {
        return UniflowWrapper.type(type.supertype_field);
    }
}
