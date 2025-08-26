package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

public interface UniFieldAccess extends UniAnnotationValue, UniExpression {

    @NotNull UniType getSelected();

    @NotNull String getName();

}
