package org.galaxy.uniflow.api.interfaces;

import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface UniAnnotationValueSupplier {

    @NotNull UniAnnotationValue get();

}
