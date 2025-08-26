package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniSwitch extends UniStatement {

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

    @NotNull UniList<UniCase> getCases();

}
