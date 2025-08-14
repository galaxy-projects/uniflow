package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

public interface UniParameter extends UniAnnotationHolder {

    @NotNull String getName();

    @NotNull UniType getType();

}
