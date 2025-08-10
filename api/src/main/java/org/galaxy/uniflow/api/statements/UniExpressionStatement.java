package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniExpressionStatement extends UniStatement {

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

}
