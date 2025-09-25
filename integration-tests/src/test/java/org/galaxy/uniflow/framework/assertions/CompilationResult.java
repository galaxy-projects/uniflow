package org.galaxy.uniflow.framework.assertions;

import org.junit.jupiter.api.Assertions;

import java.util.function.Consumer;

public final class CompilationResult {

    private final boolean success;
    private final ClassLoader classLoader;

    public CompilationResult(boolean success, ClassLoader classLoader) {
        this.success = success;
        this.classLoader = classLoader;
    }

    public CompilationResult assertFailed() {
        Assertions.assertFalse(success, "Compilation should have failed");
        return this;
    }

    public CompilationResult assertSuccess() {
        Assertions.assertTrue(success, "Compilation should have succeeded");
        return this;
    }

    public CompilationResult assertClass(String className) {
        return assertClass(className, null);
    }

    public CompilationResult assertClass(String className, Consumer<CompiledClass> consumer) {
        try {
            Class<?> clazz = classLoader.loadClass(className);

            if (consumer != null)
                consumer.accept(new CompiledClass(clazz));
            return this;
        } catch (ClassNotFoundException e) {
            Assertions.fail(e);
            return this;
        }
    }
}
