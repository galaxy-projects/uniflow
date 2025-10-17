package org.galaxy.uniflow.framework.assertions;

import java.util.function.Consumer;

public interface CompiledAnnotationHolder<T extends CompiledAnnotationHolder<T>> {

    T assertAnnotation(String annotationName);

    T assertAnnotations(String... annotationNames);

    T assertAnnotation(String annotationName, Consumer<CompiledAnnotation> consumer);

}
