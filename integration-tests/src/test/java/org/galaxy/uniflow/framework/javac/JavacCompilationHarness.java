package org.galaxy.uniflow.framework.javac;

import org.galaxy.uniflow.api.processing.UniProcessor;
import org.galaxy.uniflow.framework.CompilationHarness;
import org.galaxy.uniflow.framework.Resource;
import org.galaxy.uniflow.framework.assertions.CompilationResult;
import org.galaxy.uniflow.framework.javac.assertions.JavacCompilationResult;
import org.galaxy.uniflow.javac.UniflowAnnotationProcessor;

import javax.tools.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavacCompilationHarness implements CompilationHarness {

    public JavacCompilationHarness() {}

    @Override
    public CompilationResult compile(UniProcessor processor, Resource resource, Resource... resources) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        try (StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
             InMemoryFileManager fileManager = new InMemoryFileManager(stdManager)) {
            List<InMemoryJavaFileObject> fileObjects = new ArrayList<>(resources.length + 1);

            fileObjects.add(new InMemoryJavaFileObject(resource.className(), resource.source()));
            for (Resource res : resources) {
                fileObjects.add(new InMemoryJavaFileObject(res.className(), res.source()));
            }
            JavaCompiler.CompilationTask task = compiler.getTask(
                    null,
                    fileManager,
                    diagnostics,
                    Collections.emptyList(),
                    null,
                    fileObjects);

            task.setProcessors(Collections.singletonList(new UniflowAnnotationProcessor(processor)));
            boolean success = task.call();

            if (!success)
                return new JavacCompilationResult(false, null, diagnostics.getDiagnostics());

            return new JavacCompilationResult(true, new InMemoryClassLoader(fileManager.getCompiledClasses()),
                    diagnostics.getDiagnostics());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
