package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniWildcardType;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacWildcardType extends JavacType<Type.WildcardType> implements UniWildcardType {

    public JavacWildcardType(Type.WildcardType type) {
        super(type);
    }

    @Override
    public @NotNull UniType getType() {
        return UniUtils.type(type.type);
    }

    @Override
    public @Nullable UniType getBound() {
        return UniUtils.type(type.bound);
    }

    @Override
    public @NotNull BoundKind getBoundKind() {
        return EnumUtils.convert(BoundKind.class, type.kind);
    }
}
