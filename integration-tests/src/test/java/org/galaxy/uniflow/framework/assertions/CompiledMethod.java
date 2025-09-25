package org.galaxy.uniflow.framework.assertions;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.function.Consumer;

public final class CompiledMethod extends CompiledAnnotationHolder<CompiledMethod> {

    private final Method method;

    public CompiledMethod(Method method) {
        super(method);
        this.method = method;
    }

    public CompiledMethod assertReturnType(Type returnType) {
        Assertions.assertEquals(returnType, method.getGenericReturnType(), "Return type mismatch");
        return this;
    }

    public void assertParameterCount(int count) {
        Assertions.assertEquals(count, method.getParameterCount(), "Parameter count mismatch");
    }

    public CompiledMethod assertParameter(int index) {
        return assertParameter(index, null);
    }

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
            consumer.accept(new CompiledParameter(parameter));
        return this;
    }

    public Object execute(Object instance, Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (Exception e) {
            Assertions.fail("Method " + method.getName() + " threw an exception", e);
            return null;
        }
    }
}
