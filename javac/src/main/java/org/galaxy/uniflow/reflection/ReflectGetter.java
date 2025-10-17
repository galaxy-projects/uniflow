package org.galaxy.uniflow.reflection;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class ReflectGetter<T> implements Supplier<T> {

    private final Field field;
    private final Object holder;

    public ReflectGetter(Field field, Object holder) {
        this.field = field;
        this.holder = holder;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get() {
        try {
            return (T) field.get(holder);
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
