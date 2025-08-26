package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniMethodInvocation extends UniExpression {

    @NotNull UniList<@NotNull UniExpression> getTypeArguments();

    void setMethodSelect(@NotNull UniExpression methodSelect);

    @NotNull UniExpression getMethodSelect();

    @NotNull UniList<UniExpression> getArguments();

}
