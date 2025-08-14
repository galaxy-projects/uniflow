package org.galaxy.uniflow.api.types;

import org.jetbrains.annotations.NotNull;

public interface UniParameterizedType extends UniType {

    @NotNull UniType getType();

    @NotNull UniTypeList getTypeArguments();

}
