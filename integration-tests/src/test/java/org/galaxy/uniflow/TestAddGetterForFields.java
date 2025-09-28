package org.galaxy.uniflow;

import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.api.factories.UniMessenger;
import org.galaxy.uniflow.api.factories.UniMethodFinder;
import org.galaxy.uniflow.api.processing.UniProcessingEnvironment;
import org.galaxy.uniflow.api.processing.UniProcessor;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.statements.UniField;
import org.galaxy.uniflow.api.types.UniType;
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

public class TestAddGetterForFields {

    private final String GETTER_SOURCE = """
            package demo;
            
            import java.lang.annotation.*;
            
            @Target(ElementType.FIELD)
            @Retention(RetentionPolicy.SOURCE)
            public @interface Getter {}
            """;

    @IntegrationTest
    public void addGetterForFields(CompilationHarness harness) {
        String source = """
                package demo;
                public class Test {
                    @Getter
                    private String message = "Hello from message";
                }
                """;
        harness.compile(new AddGetterForFieldProcessor(),
                        new Resource("demo.Getter", GETTER_SOURCE),
                        new Resource("demo.Test", source))
                .assertSuccess()
                .assertClass("demo.Test", testClass -> {
                    testClass.assertField("message", field -> {
                        field.assertType(String.class);
                        field.assertModifier(Modifier.PRIVATE);
                    });
                    testClass.assertMethod("getMessage", get -> {
                        get.assertReturnType(String.class);
                        get.assertParameterCount(0);
                        get.assertExecute("Hello from message");
                    });
                });
    }

    @IntegrationTest
    public void addExistingGetterForFields(CompilationHarness harness) {
        String source = """
                package demo;
                public class Test {
                    @Getter
                    private String message = "Hello from message";
                
                    public String getMessage() {
                        return this.message;
                    }
                }
                """;
        harness.compile(new AddGetterForFieldProcessor(),
                        new Resource("demo.Getter", GETTER_SOURCE),
                        new Resource("demo.Test", source))
                .assertSuccess()
                .assertLogs(logs -> {
                    logs.assertNotEmpty();
                    logs.assertLog(CompilationLog.LogKind.NOTE, null, "Getter already exist for field message");
                })
                .assertClass("demo.Test", testClass -> {
                    testClass.assertField("message", field -> {
                        field.assertType(String.class);
                        field.assertModifier(Modifier.PRIVATE);
                    });
                    testClass.assertMethod("getMessage", get -> {
                        get.assertReturnType(String.class);
                        get.assertParameterCount(0);
                        get.assertExecute("Hello from message");
                    });
                });
    }

    static class AddGetterForFieldProcessor implements UniProcessor {

        @Override
        public @NotNull Stream<@NotNull Class<?>> getSupportedAnnotations() {
            return Stream.empty();
        }

        @Override
        public @NotNull Set<@NotNull String> getSupportedAnnotationTypes() {
            return Set.of("demo.Getter");
        }

        @Override
        public @NotNull SourceVersion getSupportedSourceVersion() {
            return SourceVersion.RELEASE_8;
        }

        @Override
        public boolean process(@NotNull Uniflow uniflow, @NotNull UniProcessingEnvironment environment) {
            if (environment.processingOver()) return true;

            UniElementFactory factory = uniflow.getElementFactory();
            UniMethodFinder methods = uniflow.getFinder().methods();

            environment.getElementStreamAnnotatedWithByName("demo.Getter")
                    .filter(UniField.class::isInstance)
                    .map(UniField.class::cast)
                    .forEach(field -> {
                        UniClass owner = field.getEnclosingClass();

                        if (owner == null) return;
                        String getterName = "get" + Character.toUpperCase(field.getName().charAt(0)) +
                                field.getName().substring(1);
                        UniMethodSignature signature = methods.find(owner.asType(), getterName, field.getType(),
                                new UniType[0]);

                        if (signature != null) {
                            uniflow.getMessenger().printMessage(UniMessenger.MessageKind.NOTE,
                                    "Getter already exist for field " + field.getName());
                            return;
                        }
                        UniMethod getter = factory.createMethod(
                                factory.createModifiers(List.of(UniModifier.PUBLIC), List.of()),
                                getterName,
                                field.getType(),
                                List.of(),
                                null,
                                List.of(),
                                List.of(),
                                factory.createBlock(false, List.of(
                                        factory.createReturn(
                                                factory.createFieldAccess(UniConstants.THIS, field.getName())
                                        )
                                ))
                        );
                        owner.getMethods().addLast(getter);
                    });
            return true;
        }
    }
}
