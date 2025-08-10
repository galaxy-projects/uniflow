package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniNewArray extends UniExpression, UniAnnotationHolder {

    @Nullable UniType getType();

    @NotNull UniExpressionList getDimensions();

    @NotNull UniExpressionList getInitializers();

    @NotNull UniList<UniAnnotationHolder> getDimAnnotations();

}
