package org.galaxy.uniflow.api.expressions.pattern;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniGuardedPattern extends UniPattern {

    void setPattern(@NotNull UniPattern pattern);

    @NotNull UniPattern getPattern();

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

}
