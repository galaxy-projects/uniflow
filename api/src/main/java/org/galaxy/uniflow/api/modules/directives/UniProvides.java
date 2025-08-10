package org.galaxy.uniflow.api.modules.directives;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniExpressionList;
import org.jetbrains.annotations.NotNull;

public interface UniProvides extends UniDirective {

    void setServiceName(@NotNull UniExpression value);

    @NotNull UniExpression getServiceName();

    @NotNull UniExpressionList getImplementationNames();

}
