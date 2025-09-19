package org.galaxy.uniflow.api.types;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniParameterizedType extends UniType {

    @NotNull UniType getType();

    @NotNull UniList<@NotNull UniType> getTypeArguments();

}
