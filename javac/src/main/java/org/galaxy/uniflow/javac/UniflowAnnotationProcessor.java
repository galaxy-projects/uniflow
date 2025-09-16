package org.galaxy.uniflow.javac;

import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.factories.UniRoundEnvironment;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;

public abstract class UniflowAnnotationProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        JavacUniflow.create(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        JavacUniflow uniflow = JavacUniflow.getInstance();
        UniRoundEnvironment roundEnvironment = uniflow.createRoundEnvironment(roundEnv);

        return process(uniflow, roundEnvironment);
    }

    protected abstract boolean process(@NotNull Uniflow uniflow, @NotNull UniRoundEnvironment roundEnvironment);
}
