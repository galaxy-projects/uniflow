package org.galaxy.uniflow.api.modules.directives;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniUses extends UniDirective {

    void setServiceName(@NotNull UniExpression serviceName);

    @NotNull UniExpression getServiceName();

}
