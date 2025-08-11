package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.types.UniClassType;
import org.jetbrains.annotations.NotNull;

public interface UniElementFactory {

    
    @NotNull UniClassType findClass(@NotNull String name);

}
