package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.jetbrains.annotations.NotNull;

public interface UniOperatorExpression extends UniExpression {

    void setOperator(@NotNull UniOperatorSignature operator);

    @NotNull UniOperatorSignature getOperator();

}
