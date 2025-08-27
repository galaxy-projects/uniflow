package org.galaxy.uniflow.api.pattern;

import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;

public interface UniBindingPattern extends UniPattern {

    void setVariable(@NotNull UniVariable variable);

    @NotNull UniVariable getVariable();

}
