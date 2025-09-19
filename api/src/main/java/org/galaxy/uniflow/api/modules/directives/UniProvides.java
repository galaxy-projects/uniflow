package org.galaxy.uniflow.api.modules.directives;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniProvides extends UniDirective {

    void setServiceName(@NotNull UniExpression serviceName);

    @NotNull UniExpression getServiceName();

    @NotNull UniList<@NotNull UniExpression> getImplementationNames();

}
