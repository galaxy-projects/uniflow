package org.galaxy.uniflow.javac.elements.resources;

import org.galaxy.uniflow.api.elements.resources.UniVariableResource;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;

public class JavacVariableResource extends JavacResource<UniVariable> implements UniVariableResource {

    private UniVariable variable;

    public JavacVariableResource(UniVariable variable) {
        this.variable = variable;
    }

    @Override
    public void setVariable(@NotNull UniVariable variable) {
        this.variable = variable;
    }

    @Override
    public @NotNull UniVariable getVariable() {
        return variable;
    }

    @Override
    public UniVariable getElement() {
        return variable;
    }
}
