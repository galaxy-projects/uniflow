package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.signatures.UniFieldSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface UniFieldFinder {

    @Nullable UniFieldSignature find(@NotNull UniClassType owner,
                                     @NotNull String name);

    @Nullable UniFieldSignature find(@NotNull Class<?> owner,
                                     @NotNull String name);

    @NotNull List<UniFieldSignature> find(@NotNull UniClassType owner, @NotNull UniType fieldType);

    @NotNull List<UniFieldSignature> find(@NotNull Class<?> owner, @NotNull UniType fieldType);

    @NotNull List<UniFieldSignature> find(@NotNull UniClassType owner, @NotNull Class<?> fieldType);

    @NotNull List<UniFieldSignature> find(@NotNull Class<?> owner, @NotNull Class<?> fieldType);

}
