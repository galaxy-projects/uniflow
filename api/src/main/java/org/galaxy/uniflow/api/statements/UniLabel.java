package org.galaxy.uniflow.api.statements;

import org.jetbrains.annotations.NotNull;

public interface UniLabel extends UniStatement {

    void setLabel(@NotNull String label);

    @NotNull String getLabel();

    void setBody(@NotNull UniStatement body);

    @NotNull UniStatement getBody();

}
