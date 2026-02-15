package org.galaxy.uniflow.intellij.psi.util;

import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.stream.Stream;

public class IJUtils {

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
}
