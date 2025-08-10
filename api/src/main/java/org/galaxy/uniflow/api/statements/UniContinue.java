package org.galaxy.uniflow.api.statements;

import org.jetbrains.annotations.Nullable;

public interface UniContinue extends UniStatement {

    void setLabel(@Nullable String label);

    @Nullable String getLabel();

}
