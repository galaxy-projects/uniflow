package org.galaxy.uniflow.javac;

import org.galaxy.uniflow.api.processing.UniProcessingEnvironment;
import org.galaxy.uniflow.api.processing.UniProcessor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

public class UniflowAnnotationProcessor extends AbstractProcessor {

    private final UniProcessor processor;

    public UniflowAnnotationProcessor(UniProcessor processor) {
        this.processor = processor;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processor.getSupportedSourceVersion();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return processor.getSupportedAnnotationTypes();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        JavacUniflow.create(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        JavacUniflow uniflow = JavacUniflow.getInstance();
        UniProcessingEnvironment environment = uniflow.createRoundEnvironment(roundEnv);

        return processor.process(uniflow, environment);
    }
}
