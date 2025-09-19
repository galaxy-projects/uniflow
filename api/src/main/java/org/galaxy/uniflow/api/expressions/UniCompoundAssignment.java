package org.galaxy.uniflow.api.expressions;

import org.jetbrains.annotations.NotNull;

public interface UniCompoundAssignment extends UniOperatorExpression {

    void setVariable(@NotNull UniExpression variable);

    @NotNull UniExpression getVariable();

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

}
