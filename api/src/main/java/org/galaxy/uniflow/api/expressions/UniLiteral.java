package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.types.TypeTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniLiteral extends UniAnnotationValue, UniExpression {

    @NotNull TypeTag getTypeTag();

    void setValue(@Nullable Object value);

    @Nullable Object getValue();

}
