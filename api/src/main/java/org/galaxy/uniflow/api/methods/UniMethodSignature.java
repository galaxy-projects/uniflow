package org.galaxy.uniflow.api.methods;

import org.galaxy.uniflow.api.parameters.UniParameter;
import org.galaxy.uniflow.api.types.UniTypeName;
import org.jetbrains.annotations.NotNull;

public interface UniMethodSignature {

    @NotNull UniTypeName getName();

    @NotNull UniParameter @NotNull [] getParameters();

}
