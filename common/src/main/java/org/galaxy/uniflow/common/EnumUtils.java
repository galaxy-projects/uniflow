package org.galaxy.uniflow.common;

public class EnumUtils {

    public static <TARGET extends Enum<TARGET>, E extends Enum<E>> TARGET convert(Class<TARGET> targetClass, E value) {
        return Enum.valueOf(targetClass, value.name());
    }
}
