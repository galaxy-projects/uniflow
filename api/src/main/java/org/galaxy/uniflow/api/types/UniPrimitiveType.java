package org.galaxy.uniflow.api.types;

import org.jetbrains.annotations.NotNull;

public interface UniPrimitiveType extends UniType {

    void setTag(@NotNull TypeTag typeTag);

    @NotNull TypeTag getTag();

}
