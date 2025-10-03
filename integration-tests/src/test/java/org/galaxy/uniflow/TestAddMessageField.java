package org.galaxy.uniflow;

import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.api.factories.UniTypes;
import org.galaxy.uniflow.api.processing.UniProcessingEnvironment;
import org.galaxy.uniflow.api.processing.UniProcessor;
import org.galaxy.uniflow.api.statements.UniField;
import org.galaxy.uniflow.framework.CompilationHarness;
import org.galaxy.uniflow.framework.Resource;
import org.galaxy.uniflow.framework.assertions.CompilationLog;
import org.galaxy.uniflow.junit.IntegrationTest;
import org.jetbrains.annotations.NotNull;

import javax.lang.model.SourceVersion;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class TestAddMessageField {

    @IntegrationTest
    public void addField(CompilationHarness harness) {
        String source = """
                package demo;
                
                @TestAnnotation
                public class Test {
                }
                """;

        harness.compile(new AddMessageFieldProcessor(), new Resource("demo.Test", source), Sources.TEST_ANNOTATION_TYPE)
                .assertSuccess()
                .assertClass("demo.Test", testClass -> {
                    testClass.assertField("message", field -> {
                        field.assertType(String.class);
                        field.assertModifier(Modifier.PUBLIC);
                    });
                });
    }

    @IntegrationTest
    public void failAddField(CompilationHarness harness) {
        String source = """
                package demo;
                
                @TestAnnotation
                public class Test {
                    private String message;
                }
                """;

        harness.compile(new AddMessageFieldProcessor(), new Resource("demo.Test", source), Sources.TEST_ANNOTATION_TYPE)
                .assertFailed()
                .assertLogs(logs -> {
                    logs.assertNotEmpty();
                    logs.assertLogCount(CompilationLog.LogKind.ERROR, 1);
                });
    }

    static class AddMessageFieldProcessor implements UniProcessor {

        @Override
        public @NotNull Stream<@NotNull Class<?>> getSupportedAnnotations() {
            return Stream.empty();
        }

        @Override
        public @NotNull Set<@NotNull String> getSupportedAnnotationTypes() {
            return Set.of(Sources.ANNOTATION_NAME);
        }

        @Override
        public @NotNull SourceVersion getSupportedSourceVersion() {
            return SourceVersion.RELEASE_8;
        }

        @Override
        public boolean process(@NotNull Uniflow uniflow, @NotNull UniProcessingEnvironment environment) {
            if (environment.processingOver()) return true;

            UniElementFactory factory = uniflow.getElementFactory();
            environment.getElementStreamAnnotatedWithByName(Sources.ANNOTATION_NAME)
                    .filter(e -> e instanceof UniClass)
                    .map(UniClass.class::cast)
                    .forEach(uniClass -> {
                        UniField field = factory.createField(
                                factory.createModifiers(List.of(UniModifier.PUBLIC), List.of()),
                                "message",
                                UniTypes.STRING,
                                factory.createStringLiteral("Hello")
                        );
                        uniClass.getFields().addLast(field);
                    });

            return true;
        }
    }
}
