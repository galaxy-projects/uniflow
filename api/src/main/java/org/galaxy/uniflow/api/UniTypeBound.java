package org.galaxy.uniflow.api;

import org.jetbrains.annotations.NotNull;

public interface UniTypeBound extends UniElement {

    void setKind(@NotNull BoundKind kind);

    @NotNull BoundKind getBoundKind();

    enum BoundKind {
        EXTENDS,
        SUPER,
        UNBOUND
    }
}
