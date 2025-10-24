package org.galaxy.uniflow.api.modules.directives;

import org.jetbrains.annotations.NotNull;

public interface UniRequires extends UniDirective {

    void setStatic(boolean isStatic);

    boolean isStatic();

    void setTransitive(boolean transitive);

    boolean isTransitive();

    void setModuleName(@NotNull String moduleName);

    @NotNull String getModuleName();

}
