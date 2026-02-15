package org.galaxy.uniflow.api.elements.resources;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniExpressionResource extends UniResource {

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

}
