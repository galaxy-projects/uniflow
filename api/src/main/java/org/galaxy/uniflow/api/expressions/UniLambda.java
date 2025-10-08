package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.lists.UniParameterList;
import org.jetbrains.annotations.NotNull;

public interface UniLambda extends UniExpression {

    @NotNull UniParameterList getParameters();

    @NotNull UniElement getBody();

    @NotNull BodyKind getBodyKind();

    enum BodyKind {
        EXPRESSION,
        STATEMENT
    }
}
