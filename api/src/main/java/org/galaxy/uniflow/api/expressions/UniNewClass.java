package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniNewClass extends UniExpression {

    @Nullable UniExpression getEnclosingExpression();

    @NotNull UniList<@NotNull UniType> getTypeArguments();

    @NotNull UniExpression getIdentifier();

    @NotNull UniList<@NotNull UniExpression> getArguments();

    @NotNull UniClassType getClassName();

}
