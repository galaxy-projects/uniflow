package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UniMethodFinder {

    // no parameters methods
    @NotNull UniMethodSignature find(@NotNull UniClassType owner,
                                     @NotNull String name,
                                     @NotNull UniType returnType);

    // methods with parameters
    @NotNull UniMethodSignature find(@NotNull UniClassType owner,
                                     @NotNull String name,
                                     @NotNull UniType returnType,
                                     @NotNull UniType[] parameterTypes);

    // methods with parameters and throws
    @NotNull UniMethodSignature find(@NotNull UniClassType owner,
                                     @NotNull String name,
                                     @NotNull UniType returnType,
                                     @NotNull UniType[] parameterTypes,
                                     @NotNull UniType[] thrownTypes);

    // methods with parameters
    @NotNull UniMethodSignature find(@NotNull UniClassType owner,
                                     @NotNull String name,
                                     @NotNull UniType returnType,
                                     @NotNull List<UniType> parameterTypes);

    // methods with parameters and throws
    @NotNull UniMethodSignature find(@NotNull UniClassType owner,
                                     @NotNull String name,
                                     @NotNull UniType returnType,
                                     @NotNull List<UniType> parameterTypes,
                                     @NotNull List<UniType> thrownTypes);

    // no parameters methods
    // uses Class instead of UniClassType
    @NotNull UniMethodSignature find(@NotNull Class<?> owner,
                                     @NotNull String name,
                                     @NotNull UniType returnType);

    // methods with parameters
    // uses Class instead of UniClassType
    @NotNull UniMethodSignature find(@NotNull Class<?> owner,
                                     @NotNull String name,
                                     @NotNull UniType returnType,
                                     @NotNull UniType[] parameterTypes);

    // methods with parameters and throws
    // uses Class instead of UniClassType
    @NotNull UniMethodSignature find(@NotNull Class<?> owner,
                                     @NotNull String name,
                                     @NotNull UniType returnType,
                                     @NotNull UniType[] parameterTypes,
                                     @NotNull UniType[] thrownTypes);

    // methods with parameters
    // uses Class instead of UniClassType
    @NotNull UniMethodSignature find(@NotNull Class<?> owner,
                                     @NotNull String name,
                                     @NotNull UniType returnType,
                                     @NotNull List<UniType> parameterTypes);

    // methods with parameters and throws
    // uses Class instead of UniClassType
    @NotNull UniMethodSignature find(@NotNull Class<?> owner,
                                     @NotNull String name,
                                     @NotNull UniType returnType,
                                     @NotNull List<UniType> parameterTypes,
                                     @NotNull List<UniType> thrownTypes);
}
