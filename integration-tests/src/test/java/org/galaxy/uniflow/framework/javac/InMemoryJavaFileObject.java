package org.galaxy.uniflow.framework.javac;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

public class InMemoryJavaFileObject extends SimpleJavaFileObject {

    private final String source;

    public InMemoryJavaFileObject(String className, String source) {
        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.source = source;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return source;
    }
}