package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniElement;
import org.jetbrains.annotations.NotNull;

public interface UniInstanceOf extends UniExpression {

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

    @NotNull UniElement getType();

}
