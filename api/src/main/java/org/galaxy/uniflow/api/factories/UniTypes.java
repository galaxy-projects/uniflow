package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.types.TypeTag;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniPrimitiveType;
import org.galaxy.uniflow.api.types.UniType;

import java.util.UUID;

public final class UniTypes {

    public static final UniType VOID = Uniflow.getInstance().getTypeFactory().createVoidType();

    public static final UniPrimitiveType BYTE = primitive(TypeTag.BYTE);
    public static final UniPrimitiveType SHORT = primitive(TypeTag.SHORT);
    public static final UniPrimitiveType INT = primitive(TypeTag.INT);
    public static final UniPrimitiveType LONG = primitive(TypeTag.LONG);
    public static final UniPrimitiveType FLOAT = primitive(TypeTag.FLOAT);
    public static final UniPrimitiveType DOUBLE = primitive(TypeTag.DOUBLE);
    public static final UniPrimitiveType BOOLEAN = primitive(TypeTag.BOOLEAN);
    public static final UniPrimitiveType CHAR = primitive(TypeTag.CHAR);

    public static final UniClassType BOXED_BYTE = createClass(Byte.class);
    public static final UniClassType BOXED_SHORT = createClass(Short.class);
    public static final UniClassType BOXED_INT = createClass(Integer.class);
    public static final UniClassType BOXED_LONG = createClass(Long.class);
    public static final UniClassType BOXED_FLOAT = createClass(Float.class);
    public static final UniClassType BOXED_DOUBLE = createClass(Double.class);
    public static final UniClassType BOXED_BOOLEAN = createClass(Boolean.class);
    public static final UniClassType BOXED_CHAR = createClass(Character.class);

    public static final UniClassType CLASS = createClass(Class.class);
    public static final UniClassType OBJECT = createClass(Object.class);
    public static final UniClassType STRING = createClass(String.class);
    public static final UniClassType UUID = createClass(UUID.class);

    private static UniClassType createClass(Class<?> clazz) {
        return Uniflow.getInstance().getTypeFactory().createClassType(clazz);
    }

    private static UniPrimitiveType primitive(TypeTag tag) {
        return Uniflow.getInstance().getTypeFactory().createPrimitiveType(tag);
    }
}
