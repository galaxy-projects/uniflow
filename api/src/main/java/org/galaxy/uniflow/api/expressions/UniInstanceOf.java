package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.expressions.pattern.UniPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniInstanceOf extends UniExpression {

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

    void setType(@NotNull UniElement type);

    @NotNull UniElement getType();

    void setPattern(@Nullable UniPattern pattern);

    @Nullable UniPattern getPattern();

}
