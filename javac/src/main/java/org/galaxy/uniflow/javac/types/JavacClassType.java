package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacClassType extends JavacType<Type.ClassType> implements UniClassType {

    public JavacClassType(Type.ClassType type) {
        super(type);
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getParameterTypes() {
        return new JavacList<>(
                type.typarams_field,
                newList -> type.typarams_field = newList,
                UniUtils::type,
                JavacUtils::javac
        );
    }
}
