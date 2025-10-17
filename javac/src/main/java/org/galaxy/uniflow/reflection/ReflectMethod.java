package org.galaxy.uniflow.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectMethod {

    private final Method method;

    public ReflectMethod(Method method) {
        this.method = method;
    }

    public Object run(Object holder, Object... args) {
        try {
            return method.invoke(holder, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
