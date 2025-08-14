package org.galaxy.uniflow.api.signatures;

import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

public interface UniFieldSignature {

    @NotNull UniClassType getOwner();

    @NotNull UniType getType();

    @NotNull String getName();

}
