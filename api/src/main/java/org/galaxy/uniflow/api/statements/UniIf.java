package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniIf extends UniStatement {

    void setCondition(@NotNull UniExpression condition);

    @NotNull UniExpression getCondition();

    void setThenStatement(@NotNull UniStatement thenStatement);

    @NotNull UniStatement getThenStatement();

    void setElseStatement(@NotNull UniStatement elseStatement);

    @NotNull UniStatement getElseStatement();

}
