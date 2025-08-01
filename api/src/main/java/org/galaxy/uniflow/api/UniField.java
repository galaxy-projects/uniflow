package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeName;
import org.jetbrains.annotations.NotNull;

public interface UniField extends UniMember {

    @NotNull UniTypeName getName();

    @NotNull UniType getType();

}
