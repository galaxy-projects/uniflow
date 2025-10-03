package org.galaxy.uniflow.api.expressions;

import org.jetbrains.annotations.NotNull;

public interface UniArrayAccess extends UniExpression {

    void setArray(@NotNull UniExpression expression);

    @NotNull UniExpression getArray();

    void setIndex(@NotNull UniExpression index);

    @NotNull UniExpression getIndex();

}
