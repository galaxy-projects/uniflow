package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.lists.UniExpressionList;
import org.jetbrains.annotations.NotNull;

public interface UniMethodInvocation extends UniExpression {

    void setTypeArguments(@NotNull UniList<@NotNull UniElement> typeArguments);

    @NotNull UniList<@NotNull UniElement> getTypeArguments();

    void setMethodSelect(@NotNull UniExpression methodSelect);

    @NotNull UniExpression getMethodSelect();

    void setArguments(@NotNull UniExpressionList arguments);

    @NotNull UniExpressionList getArguments();

}
