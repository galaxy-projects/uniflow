package org.galaxy.uniflow.api.types;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniWildcardType extends UniType {

    @NotNull UniType getType();

    @Nullable UniType getBound();

    @NotNull BoundKind getBoundKind();

    enum BoundKind {
        EXTENDS,
        SUPER,
        UNBOUND
    }
}
