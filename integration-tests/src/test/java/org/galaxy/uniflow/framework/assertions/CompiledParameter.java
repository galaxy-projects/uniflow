package org.galaxy.uniflow.framework.assertions;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public class CompiledParameter extends CompiledAnnotationHolder<CompiledParameter> {

    private final Parameter parameter;

    public CompiledParameter(Parameter parameter) {
        super(parameter);
        this.parameter = parameter;
    }

    public CompiledParameter assertType(Type type) {
        Assertions.assertEquals(type, parameter.getParameterizedType(), "Parameter type mismatch");
        return this;
    }
}
