package org.galaxy.uniflow.api.expressions;

import org.jetbrains.annotations.NotNull;

public interface UniMemberSelect extends UniExpression {

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

    void setIdentifier(@NotNull String identifier);

    @NotNull String getIdentifier();

}
