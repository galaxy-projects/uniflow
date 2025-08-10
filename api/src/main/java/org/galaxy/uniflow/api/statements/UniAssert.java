package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniAssert extends UniStatement {

    void setCondition(@NotNull UniExpression condition);

    @NotNull UniExpression getCondition();

    void setDetails(@Nullable UniExpression details);

    @Nullable UniExpression getDetails();

}
