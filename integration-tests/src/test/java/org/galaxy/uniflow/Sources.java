package org.galaxy.uniflow;

import org.galaxy.uniflow.framework.CompilationHarness;
import org.galaxy.uniflow.framework.Resource;
import org.galaxy.uniflow.framework.javac.JavacCompilationHarness;

import java.lang.annotation.ElementType;
import java.util.stream.Stream;

public class Sources {

    public static final String ANNOTATION_NAME = "demo.TestAnnotation";

    public static final Resource TEST_ANNOTATION_TYPE = new Resource(ANNOTATION_NAME,
            createTestAnnotation(ElementType.TYPE));
    public static final Resource TEST_ANNOTATION_METHOD = new Resource(ANNOTATION_NAME,
            createTestAnnotation(ElementType.METHOD));

    public static Stream<CompilationHarness> createCompilationHarness() {
        return Stream.of(new JavacCompilationHarness());
    }

    private static String createTestAnnotation(ElementType type) {
        return """
                package demo;
                
                import java.lang.annotation.ElementType;
                import java.lang.annotation.Retention;
                import java.lang.annotation.RetentionPolicy;
                import java.lang.annotation.Target;
                
                @Retention(RetentionPolicy.RUNTIME)
                @Target(ElementType.%s)
                public @interface TestAnnotation {}
                """.formatted(type.name());
    }
}
