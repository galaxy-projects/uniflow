package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.lists.UniCaseList;
import org.jetbrains.annotations.NotNull;

public interface UniSwitchExpression extends UniExpression {

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

    @NotNull UniCaseList getCases();

}
