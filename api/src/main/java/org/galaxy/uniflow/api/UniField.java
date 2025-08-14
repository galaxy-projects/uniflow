package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

public interface UniField extends UniMember {

    @NotNull String getName();

    @NotNull UniType getType();

}
