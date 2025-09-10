package org.galaxy.uniflow.javac.factories;

import com.sun.tools.javac.api.JavacTrees;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.factories.UniRoundEnvironment;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.RoundEnvironment;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public @NotNull List<@NotNull UniElement> getElementsAnnotatedWith(@NotNull Class<? extends Annotation> a) {
        JavacTrees trees = JavacUniflow.getInstance().trees;

        return roundEnv.getElementsAnnotatedWith(a).stream()
                .map(trees::getTree)
                .filter(Objects::nonNull)
                .map(UniflowWrapper::wrap)
                .collect(Collectors.toList());
    }
}
