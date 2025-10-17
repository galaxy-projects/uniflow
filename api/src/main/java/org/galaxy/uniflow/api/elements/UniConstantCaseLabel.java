package org.galaxy.uniflow.api.elements;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniConstantCaseLabel extends UniCaseLabel {

    void setConstant(@NotNull UniExpression constant);

    @NotNull UniExpression getConstant();

}
