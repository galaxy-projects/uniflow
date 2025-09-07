package org.galaxy.uniflow.api.expressions;

import org.jetbrains.annotations.NotNull;

public interface UniUnary extends UniOperatorExpression {

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

}
