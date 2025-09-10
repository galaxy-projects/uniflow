package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.UniElement;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.List;

public interface UniRoundEnvironment {

    boolean processingOver();

    @NotNull List<@NotNull UniElement> getRootElements();

    @NotNull List<@NotNull UniElement> getElementsAnnotatedWith(@NotNull Class<? extends Annotation> annotationType);

}
