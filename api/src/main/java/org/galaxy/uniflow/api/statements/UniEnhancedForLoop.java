package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniEnhancedForLoop extends UniStatement {

    void setParameter(@NotNull UniParameter parameter);

    @NotNull UniParameter getParameter();

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

    void setBody(@NotNull UniStatement body);

    @NotNull UniStatement getBody();

}
