package org.galaxy.uniflow.api.types;

import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.jetbrains.annotations.NotNull;

public interface UniTypeParameter extends UniType, UniAnnotationHolder {

    @NotNull UniTypeName getName();

    @NotNull UniClassType getType();

    @NotNull UniTypeList getExtends();

    int getIndex();

}
