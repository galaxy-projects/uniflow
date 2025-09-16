package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.types.UniClassType;
import org.jetbrains.annotations.NotNull;

public interface UniMember extends UniElement {

    @NotNull UniModifiers getModifiers();

    @NotNull UniClassType getContainingClass();

}
