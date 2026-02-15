package org.galaxy.uniflow.api.pattern;

import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

public interface UniAnyPattern extends UniPattern {

    void setType(@NotNull UniType type);

    @NotNull UniType getType();

}
