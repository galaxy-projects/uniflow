package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.jetbrains.annotations.NotNull;

public interface UniLet extends UniExpression {

    @NotNull UniList<@NotNull UniStatement> getDefinitions();

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

}
