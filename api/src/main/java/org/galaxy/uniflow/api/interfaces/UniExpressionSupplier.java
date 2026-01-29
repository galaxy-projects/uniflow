package org.galaxy.uniflow.api.interfaces;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface UniExpressionSupplier {

    @NotNull UniExpression get();

}
