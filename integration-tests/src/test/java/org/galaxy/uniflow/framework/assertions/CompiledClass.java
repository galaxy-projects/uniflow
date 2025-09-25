package org.galaxy.uniflow.framework.assertions;

import org.intellij.lang.annotations.MagicConstant;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;

public final class CompiledClass extends CompiledAnnotationHolder<CompiledClass> {

    private final Class<?> clazz;

    public CompiledClass(Class<?> clazz) {
        super(clazz);
        this.clazz = clazz;
    }

    public CompiledClass assertField(String name) {
        return assertField(name, null);
    }

    public CompiledClass assertModifier(@MagicConstant(valuesFromClass = Modifier.class) int modifier) {
        if ((clazz.getModifiers() & modifier) == 0)
            Assertions.fail(clazz.getName() + " does not have modifier: " + modifier);
        return this;
    }

    public CompiledClass assertField(String name, Consumer<CompiledField> consumer) {
        try {
            Field field = clazz.getDeclaredField(name);

            if (consumer != null)
                consumer.accept(new CompiledField(field));
        } catch (NoSuchFieldException e) {
            Assertions.fail(e);
        }
        return this;
    }

    public CompiledClass assertMethod(String name) {
        return assertMethod(name, (Consumer<CompiledMethod>) null);
    }

    public CompiledClass assertMethod(String name, Consumer<CompiledMethod> consumer) {
        try {
            Method method = clazz.getDeclaredMethod(name);

            if (consumer != null)
                consumer.accept(new CompiledMethod(method));
        } catch (NoSuchMethodException e) {
            Assertions.fail(e);
        }
        return this;
    }

    public CompiledClass assertMethod(String name, Class<?>[] parameterTypes) {
        return assertMethod(name, parameterTypes, null);
    }

    public CompiledClass assertMethod(String name,
                                      Class<?>[] parameterTypes,
                                      Consumer<CompiledMethod> consumer) {
        try {
            Method method = clazz.getDeclaredMethod(name, parameterTypes);

            if (consumer != null)
                consumer.accept(new CompiledMethod(method));
        } catch (NoSuchMethodException e) {
            Assertions.fail(e);
        }
        return this;
    }

    public Object newInstance() {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            Assertions.fail(e);
            return null;
        }
    }
}
