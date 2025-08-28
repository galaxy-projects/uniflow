package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import org.galaxy.uniflow.api.types.UniArrayType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

public class JavacArrayType extends JavacType<Type.ArrayType> implements UniArrayType {

    public JavacArrayType(Type.ArrayType type) {
        super(type);
    }

    @Override
    public void setType(@NotNull UniType type) {
        this.type.elemtype = JavacUtils.javac(type);
    }

    @Override
    public @NotNull UniType getType() {
        return UniUtils.type(type.elemtype);
    }
}
