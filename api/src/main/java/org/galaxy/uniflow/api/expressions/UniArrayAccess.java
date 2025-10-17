package org.galaxy.uniflow.api.expressions;

import org.jetbrains.annotations.NotNull;

public interface UniArrayAccess extends UniExpression {

    void set(@NotNull UniExpression array, @NotNull UniExpression index);

    void setArray(@NotNull UniExpression array);

    @NotNull UniExpression getArray();

    void setIndex(@NotNull UniExpression index);

    @NotNull UniExpression getIndex();

}
