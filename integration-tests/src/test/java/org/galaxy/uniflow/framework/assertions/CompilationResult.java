package org.galaxy.uniflow.framework.assertions;

import java.util.function.Consumer;

public interface CompilationResult {

    CompilationResult assertFailed();

    CompilationResult assertSuccess();

    CompilationResult assertLogs(Consumer<CompilationLogList> consumer);

    CompilationResult assertClass(String className);

    CompilationResult assertClasses(String... classNames);

    CompilationResult assertClass(String className, Consumer<CompiledClass> consumer);

}
