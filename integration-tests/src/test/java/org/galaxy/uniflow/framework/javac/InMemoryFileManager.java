package org.galaxy.uniflow.framework.javac;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private final Map<String, ByteArrayJavaFileObject> compiledClasses = new HashMap<>();

    public InMemoryFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location,
                                               String className,
                                               JavaFileObject.Kind kind,
                                               FileObject sibling) throws IOException {
        ByteArrayJavaFileObject fileObject = new ByteArrayJavaFileObject(className, kind);
        compiledClasses.put(className, fileObject);
        return fileObject;
    }

    public Map<String, byte[]> getCompiledClasses() {
        return compiledClasses.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getBytes()));
    }

    @Override
    public void close() throws IOException {
        super.close();
        for (ByteArrayJavaFileObject fileObject : compiledClasses.values()) {
            fileObject.close();
        }
        compiledClasses.clear();
    }
}
