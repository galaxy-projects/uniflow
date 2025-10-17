package org.galaxy.uniflow.api.elements.labels;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

/**
 * Java 21 case labels for expressions
 */
public interface UniConstantCaseLabel extends UniCaseLabel {

    void setExpression(@NotNull UniExpression expression);

    @NotNull UniExpression getExpression();

}
