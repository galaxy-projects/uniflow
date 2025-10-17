package org.galaxy.uniflow.reflection;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class ReflectSetter<T> implements Consumer<T> {

    private final Field field;
    private final Object holder;

    public ReflectSetter(Field field, Object holder) {
        this.field = field;
        this.holder = holder;
    }

    @Override
    public void accept(T value) {
        try {
            field.set(holder, value);
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
