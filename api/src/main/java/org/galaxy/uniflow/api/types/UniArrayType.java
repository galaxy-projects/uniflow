package org.galaxy.uniflow.api.types;

import org.galaxy.uniflow.api.UniElement;
import org.jetbrains.annotations.NotNull;

public interface UniArrayType extends UniType {

    @NotNull UniElement getType();

}
