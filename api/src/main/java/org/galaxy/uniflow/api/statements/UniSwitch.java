package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniSwitch extends UniStatement {

    void setSelector(@NotNull UniExpression selector);

    @NotNull UniExpression getSelector();

    @NotNull UniList<@NotNull UniCaseBase> getCases();

}
