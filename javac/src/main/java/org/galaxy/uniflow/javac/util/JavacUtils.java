package org.galaxy.uniflow.javac.util;

import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public final class JavacUtils {

    @Contract("null, _ -> null")
    public static <T> T check(Object element, Class<T> type) {
        if (element == null) return null;
        if (!type.isInstance(element))
            throw new IllegalArgumentException("Element " + element + " is not of type " + type);
        return type.cast(element);
    }

    public static <T> Stream<T> checkList(List<?> list, Class<T> type) {
        for (Object element : list) {
            if (!type.isInstance(element))
                throw new IllegalArgumentException("Element " + element + " is not of type " + type.getName());
        }
        return list.stream().map(type::cast);
    }

    public static <T, R> com.sun.tools.javac.util.List<T> mapToList(Stream<R> stream, Function<? super R, T> mapper) {
        return stream.map(mapper).collect(com.sun.tools.javac.util.List.collector());
    }
}
