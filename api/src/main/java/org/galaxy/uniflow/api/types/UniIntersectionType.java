package org.galaxy.uniflow.api.types;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniIntersectionType extends UniType {

    @NotNull UniList<@NotNull UniElement> getBounds();

}
