package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniEnhancedForLoop extends UniStatement {

    void setVariable(@NotNull UniVariable variable);

    @NotNull UniVariable getVariable();

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

    void setBody(@NotNull UniStatement body);

    @NotNull UniStatement getBody();

}
