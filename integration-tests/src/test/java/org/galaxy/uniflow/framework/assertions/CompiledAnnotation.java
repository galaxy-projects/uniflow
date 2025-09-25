package org.galaxy.uniflow.framework.assertions;

import org.junit.jupiter.api.Assertions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CompiledAnnotation {

    private static final String ERROR = "@%s#%s should be equal to %s";

    private final Annotation annotation;

    public CompiledAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public CompiledAnnotation assertAttribute(String name, Object value) {
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
