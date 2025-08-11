package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.lists.UniExpressionList;
import org.jetbrains.annotations.NotNull;

public interface UniForLoop extends UniStatement {

    @NotNull UniStatementList getInitializer();

    void setCondition(@NotNull UniExpression condition);

    @NotNull UniExpression getCondition();

    @NotNull UniExpressionList getUpdate();

    void setBody(@NotNull UniStatement body);

    @NotNull UniStatement getBody();

}
