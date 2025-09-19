package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniDoWhileLoop extends UniStatement {

    void setCondition(@NotNull UniExpression condition);

    @NotNull UniExpression getCondition();

    void setBody(@NotNull UniStatement body);

    @NotNull UniStatement getBody();

}
