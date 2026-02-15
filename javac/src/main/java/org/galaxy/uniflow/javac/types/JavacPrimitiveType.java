package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.types.TypeTag;
import org.galaxy.uniflow.api.types.UniPrimitiveType;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacPrimitiveType extends JavacExpressionType<JCTree.JCPrimitiveTypeTree, Type.JCPrimitiveType>
        implements UniPrimitiveType {

    private static final ReflectField TAG;

    public JavacPrimitiveType(JCTree.JCPrimitiveTypeTree expression, Type.JCPrimitiveType type) {
        super(expression, type);
    }

    @Override
    public void setTag(@NotNull TypeTag typeTag) {
        TAG.set(type, EnumUtils.convert(com.sun.tools.javac.code.TypeTag.class, typeTag));
    }

    @Override
    public @NotNull TypeTag getTag() {
        return EnumUtils.convert(TypeTag.class, type.getTag());
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Type.JCPrimitiveType.class);
            TAG = type.field("tag");
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
