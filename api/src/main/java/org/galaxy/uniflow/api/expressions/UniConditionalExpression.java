package org.galaxy.uniflow.api.expressions;

import org.jetbrains.annotations.NotNull;

public interface UniConditionalExpression extends UniExpression {

    void setCondition(@NotNull UniExpression condition);

    @NotNull UniExpression getCondition();

    void setTrueExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getTrueExpression();

    void setFalseExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getFalseExpression();

}
