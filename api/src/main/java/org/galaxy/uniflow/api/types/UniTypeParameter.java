package org.galaxy.uniflow.api.types;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.jetbrains.annotations.NotNull;

public interface UniTypeParameter extends UniType, UniAnnotationHolder {

    @NotNull String getName();

    @NotNull UniType getType();

    @NotNull UniList<@NotNull UniType> getExtends();

    int getIndex();

}
