package org.galaxy.uniflow.javac.factories;

import com.sun.tools.javac.api.JavacTrees;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniModifiersHolder;
import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.factories.UniRoundEnvironment;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.RoundEnvironment;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavacRoundEnvironment implements UniRoundEnvironment {

    private final RoundEnvironment roundEnv;

    public JavacRoundEnvironment(RoundEnvironment roundEnv) {
        this.roundEnv = roundEnv;
    }

    @Override
    public boolean processingOver() {
        return roundEnv.processingOver();
    }

    @Override
    public @NotNull List<@NotNull UniElement> getRootElements() {
        JavacTrees trees = JavacUniflow.getInstance().trees;

        return roundEnv.getRootElements().stream()
                .map(trees::getTree)
                .filter(Objects::nonNull)
                .map(UniflowWrapper::wrap)
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull Stream<@NotNull UniElement> getElementStreamAnnotatedWith(
            @NotNull Class<? extends Annotation> annotationType) {
        JavacTrees trees = JavacUniflow.getInstance().trees;

        return roundEnv.getElementsAnnotatedWith(annotationType).stream()
                .map(trees::getTree)
                .filter(Objects::nonNull)
                .map(UniflowWrapper::wrap);
    }

    @Override
    public @NotNull Map<@NotNull UniElement, @NotNull UniAnnotation> getElementsAnnotatedWith(
            @NotNull Class<? extends Annotation> annotationType) {
        UniClassType classType = Uniflow.getInstance().getTypeFactory().createClassType(annotationType);

        return getElementStreamAnnotatedWith(annotationType)
                .collect(Collectors.toMap(Function.identity(), element -> {
                    UniList<@NotNull UniAnnotation> annotations;

                    if (element instanceof UniAnnotationHolder) {
                        annotations = ((UniAnnotationHolder) element).getAnnotations();
                    } else if (element instanceof UniModifiersHolder) {
                        annotations = ((UniModifiersHolder) element).getModifiers()
                                .getAnnotations();
                    } else
                        throw new RuntimeException("Element " + element + " doesn't have annotations");
                    return annotations.stream()
                            .filter(annotation -> annotation.getType().equals(classType))
                            .findFirst()
                            .orElseThrow(
                                    () -> new RuntimeException("Element " + element + " doesn't have annotations"));
                }));
    }
}
