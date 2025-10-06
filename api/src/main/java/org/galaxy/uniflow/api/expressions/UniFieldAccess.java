package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

public interface UniFieldAccess extends UniAnnotationValue, UniExpression {

    void setSelected(@NotNull UniType selected);

    @NotNull UniType getSelected();

    void setName(@NotNull String name);

    @NotNull String getName();

}
