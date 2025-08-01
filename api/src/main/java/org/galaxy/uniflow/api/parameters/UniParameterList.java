package org.galaxy.uniflow.api.parameters;

import org.galaxy.uniflow.api.types.UniTypeName;
import org.jetbrains.annotations.NotNull;

public interface UniParameterList {

    @NotNull UniParameter @NotNull [] getParameters();

    boolean hasParameters();

    boolean hasParameter(@NotNull UniTypeName name);

    void addParameter(@NotNull UniParameter parameter);

    int getParameterIndex(@NotNull UniTypeName name);

    void removeParameter(int index);

    void removeParameter(@NotNull UniParameter parameter);

}
