package org.galaxy.uniflow.api.expressions;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniEnhancedCase;
import org.jetbrains.annotations.NotNull;

public interface UniSwitchExpression extends UniExpression {

    void setSelector(@NotNull UniExpression selector);

    @NotNull UniExpression getSelector();

    @NotNull UniList<UniEnhancedCase> getCases();

}
