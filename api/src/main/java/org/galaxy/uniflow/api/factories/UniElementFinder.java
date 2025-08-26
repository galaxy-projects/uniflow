package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.signatures.UniFieldSignature;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

public interface UniElementFinder {

    @NotNull UniClassType findClass(@NotNull Class<?> clazz);

    @NotNull UniClassType findClass(@NotNull String name);

    @NotNull UniMethodSignature findMethod(@NotNull UniClassType owner,
                                           @NotNull String name,
                                           @NotNull UniType returnType,
                                           @NotNull UniType... parameterTypes);

    @NotNull UniMethodSignature findMethod(@NotNull Class<?> owner,
                                           @NotNull String name,
                                           @NotNull Class<?> returnType,
                                           @NotNull Class<?>... parameterTypes);

    @NotNull UniFieldSignature findField(@NotNull UniClassType owner,
                                         @NotNull String name);

    @NotNull UniFieldSignature findField(@NotNull Class<?> owner,
                                         @NotNull String name);

}
