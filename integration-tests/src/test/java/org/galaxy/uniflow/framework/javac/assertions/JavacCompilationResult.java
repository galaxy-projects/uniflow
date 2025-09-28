package org.galaxy.uniflow.framework.javac.assertions;

import org.galaxy.uniflow.framework.assertions.CompilationResult;
import org.galaxy.uniflow.framework.assertions.CompiledClass;
import org.junit.jupiter.api.Assertions;

import java.util.function.Consumer;

public record JavacCompilationResult(boolean success, ClassLoader classLoader) implements CompilationResult {

    @Override
    public CompilationResult assertFailed() {
        Assertions.assertFalse(success, "Compilation should have failed");
        return this;
    }

    @Override
    public CompilationResult assertSuccess() {
        Assertions.assertTrue(success, "Compilation should have succeeded");
        return this;
    }

    @Override
    public CompilationResult assertClass(String className) {
        return assertClass(className, null);
    }

    @Override
    public CompilationResult assertClasses(String... classNames) {
        for (String className : classNames)
            assertClass(className);
        return this;
    }

    @Override
    public CompilationResult assertClass(String className, Consumer<CompiledClass> consumer) {
        try {
            Class<?> clazz = classLoader.loadClass(className);

            if (consumer != null)
                consumer.accept(new JavacCompiledClass(clazz));
            return this;
        } catch (ClassNotFoundException e) {
            Assertions.fail(e);
            return this;
        }
    }
}
