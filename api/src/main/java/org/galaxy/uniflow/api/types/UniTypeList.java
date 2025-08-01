package org.galaxy.uniflow.api.types;

import org.jetbrains.annotations.NotNull;

public interface UniTypeList {

    @NotNull UniType @NotNull [] getTypes();

    void addType(@NotNull UniType type);

    void removeType(@NotNull UniType type);

}
