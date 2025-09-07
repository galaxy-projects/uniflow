package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniWildcardType;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacWildcardType extends JavacType<JCTree.JCWildcard, Type.WildcardType> implements UniWildcardType {

    public JavacWildcardType(JCTree.JCWildcard wildcard, Type.WildcardType type) {
        super(wildcard, type);
    }

    @Override
    public @NotNull UniType getType() {
        return UniflowWrapper.type(type.type);
    }

    @Override
    public @Nullable UniType getBound() {
        return UniflowWrapper.type(type.bound);
    }

    @Override
    public @NotNull BoundKind getBoundKind() {
        return EnumUtils.convert(BoundKind.class, type.kind);
    }
}
