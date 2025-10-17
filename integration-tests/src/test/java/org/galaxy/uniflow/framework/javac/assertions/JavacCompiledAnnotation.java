package org.galaxy.uniflow.framework.javac.assertions;

import org.galaxy.uniflow.framework.assertions.CompiledAnnotation;
import org.junit.jupiter.api.Assertions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public record JavacCompiledAnnotation(Annotation annotation) implements CompiledAnnotation {

    private static final String ERROR = "@%s#%s should be equal to %s";

    @Override
    public JavacCompiledAnnotation assertAttribute(String name, Object value) {
        Assertions.assertNotNull(name, "Name should not be null");
        Assertions.assertNotNull(value, "Value should not be null");

        try {
            Method method = annotation.annotationType().getDeclaredMethod(name);
            Object result = method.invoke(annotation);

            Assertions.assertEquals(value, result,
                    ERROR.formatted(annotation.annotationType().getSimpleName(), name, value));
        } catch (Exception e) {
            Assertions.fail(e);
        }
        return this;
    }
}
