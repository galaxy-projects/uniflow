package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;

public interface UniParameterList {

    @NotNull UniVariable @NotNull [] getParameters();

    boolean hasParameters();

    boolean hasParameter(@NotNull String name);

    void addParameter(@NotNull UniVariable parameter);

    int getParameterIndex(@NotNull String name);

    void removeParameter(int index);

    void removeParameter(@NotNull UniVariable parameter);

}
