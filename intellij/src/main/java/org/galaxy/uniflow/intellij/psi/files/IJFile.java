package org.galaxy.uniflow.intellij.psi.files;

import com.intellij.openapi.vfs.VirtualFile;
import org.galaxy.uniflow.api.files.UniFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URI;

public class IJFile implements UniFile {

    private final VirtualFile file;

    public IJFile(VirtualFile file) {
        this.file = file;
    }

    @Override
    public @NotNull URI getUri() {
        return file.toNioPath().toUri();
    }

    @Override
    public @NotNull String getName() {
        return file.getName();
    }

    @Override
    public @NotNull InputStream openInputStream() throws IOException {
        return file.getInputStream();
    }

    @Override
    public @NotNull Reader openReader(boolean ignoreEncodingErrors) throws IOException {
        return new BufferedReader(new InputStreamReader(openInputStream()));
    }

    @Override
    public @Nullable CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(openInputStream()))) {
            StringBuilder result = new StringBuilder();

            reader.lines().forEach(line -> result.append(line).append('\n'));
            return result;
        }
    }

    @Override
    public @NotNull OutputStream openOutputStream() throws IOException {
        return file.getOutputStream(null);
    }

    @Override
    public @NotNull Writer openWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(openOutputStream()));
    }

    @Override
    public long getLastModified() {
        return file.getModificationStamp();
    }

    @Override
    public boolean delete() {
        try {
            file.delete(null);
            return true;
        } catch (IOException _e) {
            return false;
        }
    }
}
