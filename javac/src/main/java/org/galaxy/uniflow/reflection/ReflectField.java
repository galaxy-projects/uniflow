package org.galaxy.uniflow.reflection;

import java.lang.reflect.Field;

public class ReflectField {

    private final Field field;

    public ReflectField(Field field) {
        this.field = field;
    }

    public void set(Object holder, Object value) {
        try {
            field.set(holder, value);
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object holder) {
        try {
            return (T) field.get(holder);
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public <T> ReflectSetter<T> createSetter(Object holder) {
        return new ReflectSetter<>(field, holder);
    }

    public <T> ReflectGetter<T> createGetter(Object holder) {
        return new ReflectGetter<>(field, holder);
    }
}
