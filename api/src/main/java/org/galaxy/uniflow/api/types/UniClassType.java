package org.galaxy.uniflow.api.types;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniClassType extends UniType {

    @NotNull UniList<@NotNull UniType> getParameterTypes();

}
