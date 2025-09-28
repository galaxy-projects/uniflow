package org.galaxy.uniflow.framework.javac.assertions;

import org.galaxy.uniflow.framework.assertions.CompiledParameter;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public class JavacCompiledParameter extends JavacCompiledAnnotationHolder<CompiledParameter>
        implements CompiledParameter {

    private final Parameter parameter;

    public JavacCompiledParameter(Parameter parameter) {
        super(parameter);
        this.parameter = parameter;
    }

    @Override
    public CompiledParameter assertType(Type type) {
        Assertions.assertEquals(type, parameter.getParameterizedType(), "Parameter type mismatch");
        return this;
    }
}
