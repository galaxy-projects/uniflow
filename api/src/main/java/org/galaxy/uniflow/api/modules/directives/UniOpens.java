package org.galaxy.uniflow.api.modules.directives;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.lists.UniExpressionList;
import org.jetbrains.annotations.NotNull;

public interface UniOpens extends UniDirective {

    void setPackageName(@NotNull UniExpression packageName);

    @NotNull UniExpressionList getPackageName();

    @NotNull UniExpressionList getModuleNames();

}
