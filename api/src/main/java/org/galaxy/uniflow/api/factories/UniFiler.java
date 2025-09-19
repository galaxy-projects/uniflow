package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.files.UniFile;
import org.galaxy.uniflow.api.files.UniFileLocation;
import org.galaxy.uniflow.api.files.UniJavaFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Supplier;

public interface UniFiler {

    @NotNull UniFileLocation createLocation(@NotNull CharSequence name);

    @NotNull UniFileLocation createLocation(@NotNull CharSequence packageName,
                                            @NotNull CharSequence name);

    @NotNull UniFileLocation createLocation(@NotNull CharSequence module,
                                            @NotNull CharSequence packageName,
                                            @NotNull CharSequence name);

    @NotNull UniFileLocation createLocation(@NotNull UniFileLocation.Location location, @NotNull CharSequence name);

    @NotNull UniFileLocation createLocation(@NotNull UniFileLocation.Location location,
                                            @NotNull CharSequence packageName,
                                            @NotNull CharSequence name);

    @NotNull UniFileLocation createLocation(@NotNull UniFileLocation.Location location,
                                            @NotNull CharSequence module,
                                            @NotNull CharSequence packageName,
                                            @NotNull CharSequence name);

    @NotNull UniJavaFile createSourceFile(@NotNull CharSequence name, @NotNull Supplier<@NotNull String> contents)
            throws IOException;

    @NotNull UniFile createResource(@NotNull UniFileLocation location, @NotNull Supplier<@NotNull String> contents)
            throws IOException;

    @NotNull UniFile getResource(@NotNull UniFileLocation location) throws IOException;

}
