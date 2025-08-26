package org.galaxy.uniflow.api.modules;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.jetbrains.annotations.NotNull;

public interface UniModule extends UniElement, UniAnnotationHolder {

    void setKind(@NotNull ModuleKind kind);

    @NotNull ModuleKind getModuleKind();

    void setName(@NotNull UniExpression name);

    @NotNull UniExpression getName();

    @NotNull UniList<UniDirective> getDirectives();

    enum ModuleKind {
        OPEN,
        STRONG
    }
}
