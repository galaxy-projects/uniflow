package org.galaxy.uniflow.api.parameters;

import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeName;
import org.jetbrains.annotations.NotNull;

public interface UniParameter extends UniAnnotationHolder {

    @NotNull UniTypeName getName();

    @NotNull UniType getType();

}
