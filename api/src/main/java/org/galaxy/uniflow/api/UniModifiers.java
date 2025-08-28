package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.jetbrains.annotations.NotNull;

public interface UniModifiers extends UniAnnotationHolder {

    @NotNull UniModifier @NotNull [] getModifiers();

    boolean hasModifier(@NotNull UniModifier modifier);

    void addModifier(@NotNull UniModifier modifier);

    void removeModifier(@NotNull UniModifier modifier);

}
