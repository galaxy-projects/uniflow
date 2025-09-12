package org.galaxy.uniflow.api.types;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public enum TypeTag {
    /**
     * The tag of the basic type `byte'.
     */
    BYTE,

    /**
     * The tag of the basic type `char'.
     */
    CHAR,

    /**
     * The tag of the basic type `short'.
     */
    SHORT,

    /**
     * The tag of the basic type `long'.
     */
    LONG,

    /**
     * The tag of the basic type `float'.
     */
    FLOAT,
    /**
     * The tag of the basic type `int'.
     */
    INT,
    /**
     * The tag of the basic type `double'.
     */
    DOUBLE,
    /**
     * The tag of the basic type `boolean'.
     */
    BOOLEAN,

    /**
     * The tag of the basic type 'void'
     */
    VOID;

    private static final HashMap<Class<?>, TypeTag> TAG_BY_TYPE = new HashMap<>();


    public static @NotNull TypeTag fromPrimitiveType(@NotNull Class<?> type) {
        return TAG_BY_TYPE.get(type);
    }

    static {
        TAG_BY_TYPE.put(Byte.TYPE, BYTE);
        TAG_BY_TYPE.put(Short.TYPE, SHORT);
        TAG_BY_TYPE.put(Integer.TYPE, INT);
        TAG_BY_TYPE.put(Long.TYPE, LONG);
        TAG_BY_TYPE.put(Float.TYPE, FLOAT);
        TAG_BY_TYPE.put(Double.TYPE, DOUBLE);
        TAG_BY_TYPE.put(Boolean.TYPE, BOOLEAN);
        TAG_BY_TYPE.put(Character.TYPE, CHAR);
        TAG_BY_TYPE.put(Void.TYPE, VOID);
    }
}
