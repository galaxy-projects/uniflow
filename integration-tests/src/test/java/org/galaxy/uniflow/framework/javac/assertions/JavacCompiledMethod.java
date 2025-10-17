package org.galaxy.uniflow.framework.javac.assertions;

import org.galaxy.uniflow.framework.assertions.CompiledMethod;
import org.galaxy.uniflow.framework.assertions.CompiledParameter;
import org.intellij.lang.annotations.MagicConstant;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.function.Consumer;

public final class JavacCompiledMethod extends JavacCompiledAnnotationHolder<CompiledMethod> implements CompiledMethod {

    private final Method method;

    public JavacCompiledMethod(Method method) {
        super(method);
        this.method = method;
    }

    @Override
    public CompiledMethod assertModifier(@MagicConstant(valuesFromClass = Modifier.class) int modifier) {
        if ((method.getModifiers() & modifier) == 0)
            Assertions.fail(method.getName() + " does not have modifier: " + modifier);
        return this;
    }

    @Override
    public CompiledMethod assertReturnType(Type returnType) {
        Assertions.assertEquals(returnType, method.getGenericReturnType(), "Return type mismatch");
        return this;
    }

    @Override
    public CompiledMethod assertParameterCount(int count) {
        Assertions.assertEquals(count, method.getParameterCount(), "Parameter count mismatch");
        return this;
    }

    @Override
    public CompiledMethod assertParameter(int index) {
        return assertParameter(index, null);
    }

    @Override
    public CompiledMethod assertParameter(int index, Consumer<CompiledParameter> consumer) {
        Parameter[] parameters = method.getParameters();

        if (index < 0 || index >= parameters.length) {
            Assertions.fail("Index out of bounds for parameter " + index);
            return this;
        }
        Parameter parameter = parameters[index];

        if (parameter == null) {
            Assertions.fail("Parameter " + index + " is null");
            return this;
        }

        if (consumer != null)
            consumer.accept(new JavacCompiledParameter(parameter));
        return this;
    }

    @Override
    public CompiledMethod assertExecute(Object expected, Object... args) {
        try {
            return assertExecute(expected, method.getDeclaringClass().getDeclaredConstructor().newInstance(), args);
        } catch (Exception e) {
            Assertions.fail(e);
            return this;
        }
    }

    @Override
    public CompiledMethod assertExecute(Object expected, Object instance, Object... args) {
        try {
            Object result = method.invoke(instance, args);

            Assertions.assertEquals(expected, result, "Result mismatch");
        } catch (Exception e) {
            Assertions.fail(e);
        }
        return this;
    }
}
