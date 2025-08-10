package org.galaxy.uniflow.api;

import org.jetbrains.annotations.NotNull;

public interface UniTypeBound extends UniElement {

    void setKind(@NotNull BoundKind kind);

    @NotNull BoundKind getKind();

    enum BoundKind {
        EXTENDS,
        SUPER,
        UNBOUND
    }
}
