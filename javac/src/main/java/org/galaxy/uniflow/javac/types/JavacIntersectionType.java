package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniIntersectionType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JavacIntersectionType extends JavacType<Type.IntersectionClassType> implements UniIntersectionType {

    public JavacIntersectionType(Type.IntersectionClassType type) {
        super(type);
    }

    @Override
    public @NotNull List<@NotNull UniType> getComponents() {
        return new ArrayList<>(type.getComponents().map(UniUtils::type));
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getInterfaces() {
        return new JavacList<>(
                type.interfaces_field,
                newList -> type.interfaces_field = newList,
                UniUtils::type,
                JavacUtils::javac
        );
    }

    @Override
    public void setSupertype(@NotNull UniType supertype) {
        type.supertype_field = JavacUtils.javac(supertype);
    }

    @Override
    public @NotNull UniType getSupertype() {
        return UniUtils.type(type.supertype_field);
    }
}
