package org.galaxy.uniflow.api.modules;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.jetbrains.annotations.NotNull;

public interface UniModule extends UniElement {

    @NotNull UniModifiers getModifiers();

    @NotNull ModuleKind getModuleKind();

    void setName(@NotNull UniExpression name);

    @NotNull UniExpression getName();

    @NotNull UniList<UniDirective> getDirectives();

    enum ModuleKind {
        OPEN,
        STRONG
    }
}
