package org.galaxy.uniflow.api.types;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniUnionType extends UniType {

    @NotNull UniList<@NotNull UniType> getTypeAlternatives();

}
