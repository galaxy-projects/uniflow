package org.galaxy.uniflow.api.processing;

import org.galaxy.uniflow.api.Uniflow;
import org.jetbrains.annotations.NotNull;

import javax.lang.model.SourceVersion;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface UniProcessor {

    @NotNull Stream<@NotNull Class<?>> getSupportedAnnotations();

    @NotNull SourceVersion getSupportedSourceVersion();

    default @NotNull Set<@NotNull String> getSupportedAnnotationTypes() {
        return getSupportedAnnotations().map(Class::getName).collect(Collectors.toSet());
    }

    boolean process(@NotNull Uniflow uniflow, @NotNull UniProcessingEnvironment environment);

}
