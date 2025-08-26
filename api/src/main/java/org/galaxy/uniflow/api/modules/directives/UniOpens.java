package org.galaxy.uniflow.api.modules.directives;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniOpens extends UniDirective {

    void setPackageName(@NotNull UniExpression packageName);

    @NotNull UniList<@NotNull UniExpression> getPackageName();

    @NotNull UniList<@NotNull UniExpression> getModuleNames();

}
