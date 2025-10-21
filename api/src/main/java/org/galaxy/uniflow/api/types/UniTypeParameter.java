package org.galaxy.uniflow.api.types;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.jetbrains.annotations.NotNull;

public interface UniTypeParameter extends UniAnnotationHolder, UniElement {

    void setName(@NotNull String name);

    @NotNull String getName();

    @NotNull UniList<@NotNull UniType> getExtends();

}
