package org.galaxy.uniflow.intellij.psi.elements.resources;

import com.intellij.psi.PsiResourceListElement;
import com.intellij.psi.PsiVariable;
import org.galaxy.uniflow.api.elements.resources.UniVariableResource;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.jetbrains.annotations.NotNull;

public class IJVariableResource extends IJResource<UniVariable> implements UniVariableResource {

    private UniVariable variable;

    public IJVariableResource(UniVariable variable) {
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
    public PsiResourceListElement getResourceElement() {
        PsiVariable unwrap = IntellijUnwrapper.unwrap(variable);

        return IntellijUniflow.getInstance().factory.createResourceVariable(variable.getName(), unwrap.getType(),
                unwrap.getInitializer(), null);
    }
}
