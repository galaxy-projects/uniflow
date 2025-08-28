package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniVariable extends UniStatement {

    @NotNull UniModifiers getModifiers();

    @NotNull String getName();

    @NotNull UniType getType();

    void setInitializer(@Nullable UniExpression expression);

    @Nullable UniExpression getInitializer();

}
