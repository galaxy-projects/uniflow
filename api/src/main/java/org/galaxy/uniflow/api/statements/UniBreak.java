package org.galaxy.uniflow.api.statements;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniBreak extends UniStatement {

    void setLabel(@NotNull String label);

    @Nullable String getLabel();

}
