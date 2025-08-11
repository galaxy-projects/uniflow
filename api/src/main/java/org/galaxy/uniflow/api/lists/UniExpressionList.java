package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniExpressionList extends UniList<UniExpression> {

    default @NotNull UniExpression @NotNull [] getExpressions() {
        return get();
    }

}
