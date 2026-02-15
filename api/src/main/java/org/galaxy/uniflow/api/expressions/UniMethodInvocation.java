package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

public interface UniMethodInvocation extends UniExpression {

    @NotNull UniList<@NotNull UniType> getTypeArguments();

    void setMethodSelect(@NotNull UniExpression methodSelect);

    @NotNull UniExpression getMethodSelect();

    @NotNull UniList<@NotNull UniExpression> getArguments();

}
