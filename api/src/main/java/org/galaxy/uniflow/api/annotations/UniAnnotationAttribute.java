package org.galaxy.uniflow.api.annotations;

import org.galaxy.uniflow.api.UniElement;
import org.jetbrains.annotations.NotNull;

public interface UniAnnotationAttribute extends UniElement {

    @NotNull String getName();

    @NotNull UniAnnotationValue getValue();

}
