package org.galaxy.uniflow.api.expressions;

import org.jetbrains.annotations.NotNull;

public interface UniIdentifier extends UniExpression {

    void setName(@NotNull String name);

    @NotNull String getName();

}
