package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.types.UniClassType;
import org.jetbrains.annotations.NotNull;

public interface UniElementFinder {

    @Deprecated
    @NotNull UniClassType findClass(@NotNull Class<?> clazz);

    @Deprecated
    @NotNull UniClassType findClass(@NotNull String name);

    @NotNull UniFieldFinder fields();

    @NotNull UniMethodFinder methods();

}
