package org.galaxy.uniflow;

import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.api.factories.UniTypes;
import org.galaxy.uniflow.api.processing.UniProcessingEnvironment;
import org.galaxy.uniflow.api.processing.UniProcessor;
import org.galaxy.uniflow.api.types.TypeTag;
import org.galaxy.uniflow.framework.CompilationHarness;
import org.galaxy.uniflow.framework.Resource;
import org.galaxy.uniflow.framework.assertions.AssertStream;
import org.galaxy.uniflow.junit.IntegrationTest;
import org.jetbrains.annotations.NotNull;

import javax.lang.model.SourceVersion;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class TestAddSayHiMethod {

    @IntegrationTest
    public void sayHi(CompilationHarness harness) {
        String source = """
                package demo;
                
                @TestAnnotation
                public class Test {
                
                    public void run() {
                        System.out.println("Hello World");
                    }
                }
                """;
        harness.compile(new AddSayHiProcessor(), new Resource("demo.Test", source), Sources.TEST_ANNOTATION_TYPE)
                .assertSuccess()
                .assertClass(Sources.ANNOTATION_NAME)
                .assertClass("demo.Test", testClass -> {
                    testClass.assertMethod("run");
                    testClass.assertMethod("sayHi", sayHi -> {
                        sayHi.assertReturnType(int.class);
                        sayHi.assertParameterCount(0);
                        AssertStream.assertStdout("Hi everyone" + System.lineSeparator(), () -> {
                            sayHi.assertExecute(1234);
                        });
                    });
                });
    }

    static class AddSayHiProcessor implements UniProcessor {

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
                    .filter(element -> element instanceof UniClass)
                    .forEach(element -> {
                        UniClass uniClass = (UniClass) element;
                        UniMethod sayHi = createSayHi(factory);

                        uniClass.getMethods().addLast(sayHi);
                    });
            return true;
        }

        private UniMethod createSayHi(UniElementFactory factory) {
            return factory.createMethod(
                    factory.createModifiers(List.of(UniModifier.PUBLIC), List.of()),
                    "sayHi",
                    UniTypes.INT,
                    List.of(),
                    null,
                    List.of(),
                    List.of(),
                    factory.createBlock(false, List.of(
                            factory.createExecution(
                                    factory.createMethodInvocation(
                                            factory.createFieldAccess(
                                                    factory.createFieldAccess(System.class, "out"),
                                                    "println"
                                            ),
                                            List.of(),
                                            List.of(
                                                    factory.createStringLiteral("Hi everyone")
                                            )
                                    )
                            ),
                            factory.createReturn(factory.createLiteral(TypeTag.INT, 1234))
                    ))
            );
        }
    }
}
