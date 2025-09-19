package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniElement;
import org.jetbrains.annotations.NotNull;

public interface UniTypeCast extends UniExpression {

    void setType(@NotNull UniElement type);

    @NotNull UniElement getType();

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

}
