package org.galaxy.uniflow.framework.javac.assertions;

import org.galaxy.uniflow.framework.assertions.CompiledClass;
import org.galaxy.uniflow.framework.assertions.CompiledField;
import org.galaxy.uniflow.framework.assertions.CompiledMethod;
import org.intellij.lang.annotations.MagicConstant;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;

public final class JavacCompiledClass extends JavacCompiledAnnotationHolder<CompiledClass>
        implements CompiledClass {

    private final Class<?> clazz;

    public JavacCompiledClass(Class<?> clazz) {
        super(clazz);
        this.clazz = clazz;
    }

    @Override
    public CompiledClass assertModifier(@MagicConstant(valuesFromClass = Modifier.class) int modifier) {
        if ((clazz.getModifiers() & modifier) == 0)
            Assertions.fail(clazz.getName() + " does not have modifier: " + modifier);
        return this;
    }

    @Override
    public CompiledClass assertField(String name) {
        return assertField(name, null);
    }

    @Override
    public CompiledClass assertField(String name, Consumer<CompiledField> consumer) {
        try {
            Field field = clazz.getDeclaredField(name);

            if (consumer != null)
                consumer.accept(new JavacCompiledField(field));
        } catch (NoSuchFieldException e) {
            Assertions.fail(e);
        }
        return this;
    }

    @Override
    public CompiledClass assertFields(String... names) {
        for (String name : names)
            assertField(name);
        return this;
    }

    @Override
    public CompiledClass assertMethod(String name) {
        return assertMethod(name, (Consumer<CompiledMethod>) null);
    }

    @Override
    public CompiledClass assertMethods(String... names) {
        for (String name : names)
            assertMethod(name);
        return this;
    }

    @Override
    public CompiledClass assertMethod(String name, Consumer<CompiledMethod> consumer) {
        try {
            Method method = clazz.getDeclaredMethod(name);

            if (consumer != null)
                consumer.accept(new JavacCompiledMethod(method));
        } catch (NoSuchMethodException e) {
            Assertions.fail(e);
        }
        return this;
    }

    @Override
    public CompiledClass assertMethod(String name, Class<?>[] parameterTypes) {
        return assertMethod(name, parameterTypes, null);
    }

    @Override
    public CompiledClass assertMethod(String name,
                                      Class<?>[] parameterTypes,
                                      Consumer<CompiledMethod> consumer) {
        try {
            Method method = clazz.getDeclaredMethod(name, parameterTypes);

            if (consumer != null)
                consumer.accept(new JavacCompiledMethod(method));
        } catch (NoSuchMethodException e) {
            Assertions.fail(e);
        }
        return this;
    }
}
