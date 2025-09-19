package org.galaxy.uniflow.api.signatures;

import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

public interface UniMethodSignature {

    @NotNull UniClassType getOwner();

    @NotNull String getName();

    @NotNull UniType getReturnType();

    @NotNull UniFieldSignature @NotNull [] getParameters();

    @NotNull UniType @NotNull [] getParameterTypes();

    @NotNull UniType @NotNull [] getThrownTypes();

}
