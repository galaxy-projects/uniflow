package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniClassType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniNewClass extends UniExpression {

    void setEnclosingExpression(@Nullable UniExpression enclosingExpression);

    @Nullable UniExpression getEnclosingExpression();

    void setTypeArguments(@NotNull UniList<@NotNull UniElement> typeArguments);

    @NotNull UniList<@NotNull UniElement> getTypeArguments();

    void setIdentifier(@NotNull UniExpressionList identifier);

    @NotNull UniExpressionList getIdentifier();

    @NotNull UniExpressionList getArguments();

    @NotNull UniClassType getClassName();

}
