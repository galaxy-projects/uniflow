package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniYield extends UniStatement {

    void setValue(@NotNull UniExpression value);

    @NotNull UniExpression getValue();

}
