package org.galaxy.uniflow.api.expressions;

import org.jetbrains.annotations.Nullable;

public interface UniLiteral extends UniExpression {

    void setValue(@Nullable Object value);

    @Nullable Object getValue();

}
