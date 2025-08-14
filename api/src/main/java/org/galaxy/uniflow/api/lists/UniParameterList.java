package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniParameter;
import org.jetbrains.annotations.NotNull;

public interface UniParameterList {

    @NotNull UniParameter @NotNull [] getParameters();

    boolean hasParameters();

    boolean hasParameter(@NotNull String name);

    void addParameter(@NotNull UniParameter parameter);

    int getParameterIndex(@NotNull String name);

    void removeParameter(int index);

    void removeParameter(@NotNull UniParameter parameter);

}
