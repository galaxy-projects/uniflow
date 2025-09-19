package org.galaxy.uniflow.api.types;

import org.jetbrains.annotations.NotNull;

public interface UniArrayType extends UniType {

    void setType(@NotNull UniType type);

    @NotNull UniType getType();

}
