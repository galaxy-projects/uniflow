package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;

public interface UniLambda extends UniExpression {

    @NotNull UniList<UniVariable> getParameters();

    @NotNull UniElement getBody();

    @NotNull BodyKind getBodyKind();

    enum BodyKind {
        EXPRESSION,
        STATEMENT
    }
}
