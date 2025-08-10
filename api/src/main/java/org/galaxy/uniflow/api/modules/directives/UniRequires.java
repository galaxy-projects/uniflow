package org.galaxy.uniflow.api.modules.directives;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.jetbrains.annotations.NotNull;

public interface UniRequires extends UniDirective {

    void setStatic(boolean isStatic);

    boolean isStatic();

    void setTransitive(boolean transitive);

    boolean isTransitive();

    void setModuleName(@NotNull UniExpression moduleName);

    @NotNull UniExpression getModuleName();

}
