package org.galaxy.uniflow.api.types;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UniIntersectionType extends UniType {

    @NotNull List<@NotNull UniType> getComponents();

    @NotNull UniList<@NotNull UniType> getInterfaces();

    void setSupertype(@NotNull UniType supertype);

    @NotNull UniType getSupertype();

}
