package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.types.TypeTag;
import org.galaxy.uniflow.api.types.UniPrimitiveType;
import org.galaxy.uniflow.common.EnumUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class JavacPrimitiveType extends JavacType<JCTree.JCPrimitiveTypeTree, Type.JCPrimitiveType>
        implements UniPrimitiveType {

    private static final Field TAG;

    public JavacPrimitiveType(JCTree.JCPrimitiveTypeTree expression, Type.JCPrimitiveType type) {
        super(expression, type);
    }

    @Override
    public void setTag(@NotNull TypeTag typeTag) {
        try {
            TAG.set(type, EnumUtils.convert(com.sun.tools.javac.code.TypeTag.class, typeTag));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull TypeTag getTag() {
        return EnumUtils.convert(TypeTag.class, type.getTag());
    }

    static {
        try {
            TAG = Type.JCPrimitiveType.class.getDeclaredField("tag");
            TAG.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
