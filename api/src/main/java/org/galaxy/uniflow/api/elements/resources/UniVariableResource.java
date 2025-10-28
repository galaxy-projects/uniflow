package org.galaxy.uniflow.api.elements.resources;

import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;

public interface UniVariableResource extends UniResource {

    void setVariable(@NotNull UniVariable variable);

    @NotNull UniVariable getVariable();

}
