package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniForLoop extends UniStatement {

    @NotNull UniList<UniStatement> getInitializer();

    void setCondition(@NotNull UniExpression condition);

    @NotNull UniExpression getCondition();

    @NotNull UniList<@NotNull UniExpressionStatement> getUpdate();

    void setBody(@NotNull UniStatement body);

    @NotNull UniStatement getBody();

}
