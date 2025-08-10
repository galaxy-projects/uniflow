package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniReturn extends UniStatement {

    void setExpression(@NotNull UniExpression value);

    @NotNull UniExpression getExpression();

}
