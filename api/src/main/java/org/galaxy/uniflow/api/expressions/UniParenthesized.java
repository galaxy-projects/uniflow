package org.galaxy.uniflow.api.expressions;

import org.jetbrains.annotations.NotNull;

public interface UniParenthesized extends UniExpression {

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

}
