package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniNewArray extends UniAnnotationValue, UniExpression, UniAnnotationHolder {

    @Nullable UniType getType();

    @NotNull UniList<@NotNull UniExpression> getDimensions();

    @NotNull UniList<@NotNull UniExpression> getInitializers();

    @NotNull UniList<@NotNull UniAnnotationHolder> getDimAnnotations();

}
