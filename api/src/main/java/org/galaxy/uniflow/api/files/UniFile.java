package org.galaxy.uniflow.api.files;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URI;

public interface UniFile {

    @NotNull URI getUri();

    @NotNull String getName();

    @NotNull InputStream openInputStream() throws IOException;

    @NotNull Reader openReader(boolean ignoreEncodingErrors) throws IOException;

    @Nullable CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException;

    @NotNull OutputStream openOutputStream() throws IOException;

    @NotNull Writer openWriter() throws IOException;

    long getLastModified();

    boolean delete();

}
