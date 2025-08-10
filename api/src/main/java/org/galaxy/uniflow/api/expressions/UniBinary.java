package org.galaxy.uniflow.api.expressions;

import org.jetbrains.annotations.NotNull;

public interface UniBinary extends UniExpression {

    void setLeftOperand(@NotNull UniExpression leftOperand);

    @NotNull UniExpression getLeftOperand();

    void setRightOperand(@NotNull UniExpression rightOperand);

    @NotNull UniExpression getRightOperand();

}
