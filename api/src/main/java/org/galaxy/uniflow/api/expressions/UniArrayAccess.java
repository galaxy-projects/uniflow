package org.galaxy.uniflow.api.expressions;

import org.jetbrains.annotations.NotNull;

public interface UniArrayAccess extends UniExpression {

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

    void setIndex(@NotNull UniExpression index);

    @NotNull UniExpression getIndex();

}
