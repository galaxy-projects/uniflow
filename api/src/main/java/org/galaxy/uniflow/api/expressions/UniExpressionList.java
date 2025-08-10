package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniExpressionList extends UniList<UniExpression> {

    default @NotNull UniExpression @NotNull [] getExpressions() {
        return get();
    }

}
