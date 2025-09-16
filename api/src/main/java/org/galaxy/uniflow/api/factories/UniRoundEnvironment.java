package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface UniRoundEnvironment {

    boolean processingOver();

    @NotNull List<@NotNull UniElement> getRootElements();

    @NotNull Stream<@NotNull UniElement> getElementStreamAnnotatedWith(
            @NotNull Class<? extends Annotation> annotationType);

    @NotNull Map<@NotNull UniElement, @NotNull UniAnnotation> getElementsAnnotatedWith(
            @NotNull Class<? extends Annotation> annotationType);

}
