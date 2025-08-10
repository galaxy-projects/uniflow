package org.galaxy.uniflow.api.statements;

import org.jetbrains.annotations.NotNull;

public interface UniLabel extends UniStatement {

    @NotNull String getLabel();

    void setBody(@NotNull UniStatement body);

    @NotNull UniStatement getBody();

}
