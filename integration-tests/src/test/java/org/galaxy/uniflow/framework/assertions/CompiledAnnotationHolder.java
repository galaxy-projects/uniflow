package org.galaxy.uniflow.framework.assertions;

import org.junit.jupiter.api.Assertions;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.function.Consumer;

public class CompiledAnnotationHolder<T extends CompiledAnnotationHolder<T>> {

    private final AnnotatedElement annotatedElement;

    public CompiledAnnotationHolder(AnnotatedElement annotatedElement) {
        this.annotatedElement = annotatedElement;
    }

    public <A extends Annotation> T assertAnnotation(String annotationName) {
        return assertAnnotation(annotationName, null);
    }

    @SuppressWarnings("unchecked")
    public <A extends Annotation> T assertAnnotation(String annotationName, Consumer<CompiledAnnotation> consumer) {
        System.out.println("ANNS: " + Arrays.toString(annotatedElement.getDeclaredAnnotations()));
        for (Annotation annotation : annotatedElement.getDeclaredAnnotations()) {
            if (annotation.annotationType().getName().equals(annotationName)) {
                if (consumer != null)
                    consumer.accept(new CompiledAnnotation(annotation));
                return (T) this;
            }
        }
        Assertions.fail(annotationName + " is not annotated with @" + annotationName);
        return (T) this;
    }
}
