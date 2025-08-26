package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.elements.UniModifierHolder;
import org.jetbrains.annotations.NotNull;

public interface UniMember extends UniAnnotationHolder, UniModifierHolder {

    @NotNull UniClass getContainingClass();

}
