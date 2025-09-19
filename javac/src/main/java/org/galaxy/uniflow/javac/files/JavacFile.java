package org.galaxy.uniflow.javac.files;

import org.galaxy.uniflow.api.files.UniFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.tools.FileObject;
import java.io.*;
import java.net.URI;

public class JavacFile<T extends FileObject> implements UniFile {

    protected final T file;

    public JavacFile(T file) {
        this.file = file;
    }

    @Override
    public @NotNull URI getUri() {
        return file.toUri();
    }

    @Override
    public @NotNull String getName() {
        return file.getName();
    }

    @Override
    public @NotNull InputStream openInputStream() throws IOException {
        return file.openInputStream();
    }

    @Override
    public @NotNull Reader openReader(boolean ignoreEncodingErrors) throws IOException {
        return file.openReader(ignoreEncodingErrors);
    }

    @Override
    public @Nullable CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return file.getCharContent(ignoreEncodingErrors);
    }

    @Override
    public @NotNull OutputStream openOutputStream() throws IOException {
        return file.openOutputStream();
    }

    @Override
    public @NotNull Writer openWriter() throws IOException {
        return file.openWriter();
    }

    @Override
    public long getLastModified() {
        return file.getLastModified();
    }

    @Override
    public boolean delete() {
        return file.delete();
    }
}
