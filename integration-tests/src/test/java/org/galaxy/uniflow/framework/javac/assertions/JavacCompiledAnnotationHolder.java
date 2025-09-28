package org.galaxy.uniflow.framework.javac.assertions;

import org.galaxy.uniflow.framework.assertions.CompiledAnnotation;
import org.galaxy.uniflow.framework.assertions.CompiledAnnotationHolder;
import org.junit.jupiter.api.Assertions;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class JavacCompiledAnnotationHolder<T extends CompiledAnnotationHolder<T>>
        implements CompiledAnnotationHolder<T> {

    private final AnnotatedElement annotatedElement;

    public JavacCompiledAnnotationHolder(AnnotatedElement annotatedElement) {
        this.annotatedElement = annotatedElement;
    }

    @Override
    public T assertAnnotation(String annotationName) {
        return assertAnnotation(annotationName, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T assertAnnotations(String... annotationNames) {
        Set<String> names = new HashSet<>(Arrays.asList(annotationNames));

        for (Annotation annotation : annotatedElement.getDeclaredAnnotations()) {
            names.remove(annotation.annotationType().getName());
        }
        if (!names.isEmpty())
            Assertions.fail("Missing annotations " + names.stream().map(name -> "@" + name).toList());
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T assertAnnotation(String annotationName,
                              Consumer<CompiledAnnotation> consumer) {
        for (Annotation annotation : annotatedElement.getDeclaredAnnotations()) {
            if (annotation.annotationType().getName().equals(annotationName)) {
                if (consumer != null)
                    consumer.accept(new JavacCompiledAnnotation(annotation));
                return (T) this;
            }
        }
        Assertions.fail(annotationName + " is not annotated with @" + annotationName);
        return (T) this;
    }
}
