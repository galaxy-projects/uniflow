package org.galaxy.uniflow.framework.javac.assertions;

import org.galaxy.uniflow.framework.assertions.CompilationLogList;
import org.galaxy.uniflow.framework.assertions.CompilationResult;
import org.galaxy.uniflow.framework.assertions.CompiledClass;
import org.junit.jupiter.api.Assertions;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.List;
import java.util.function.Consumer;

public record JavacCompilationResult(boolean success, ClassLoader classLoader,
                                     List<Diagnostic<? extends JavaFileObject>> diagnostics)
        implements CompilationResult {

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
    public CompilationResult assertLogs(Consumer<CompilationLogList> consumer) {
        consumer.accept(new CompilationLogList(diagnostics.stream().map(JavacCompilationLog::new).toList()));
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
