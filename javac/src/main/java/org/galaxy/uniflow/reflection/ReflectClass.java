package org.galaxy.uniflow.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectClass {

    private final Class<?> type;

    public ReflectClass(Class<?> type) {
        this.type = type;
    }

    public ReflectField field(String name) throws NoSuchFieldException {
        Field field = type.getDeclaredField(name);

        field.setAccessible(true);
        return new ReflectField(field);
    }

    public ReflectMethod method(String name) throws NoSuchMethodException {
        Method method = type.getDeclaredMethod(name);

        method.setAccessible(true);
        return new ReflectMethod(method);
    }

    public ReflectMethod method(String name, Class<?>... argTypes) throws NoSuchMethodException {
        Method method = type.getDeclaredMethod(name, argTypes);

        method.setAccessible(true);
        return new ReflectMethod(method);
    }

    public static ReflectClass of(String name) throws ClassNotFoundException {
        return new ReflectClass(Class.forName(name));
    }
}
