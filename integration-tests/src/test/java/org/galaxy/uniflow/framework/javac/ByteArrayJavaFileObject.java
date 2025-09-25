package org.galaxy.uniflow.framework.javac;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class ByteArrayJavaFileObject extends SimpleJavaFileObject {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public ByteArrayJavaFileObject(String className, Kind kind) {
        super(URI.create("bytes:///" + className.replace('.', '/') + kind.extension), kind);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return outputStream;
    }

    public byte[] getBytes() {
        return outputStream.toByteArray();
    }

    public void close() throws IOException {
        outputStream.close();
    }
}
