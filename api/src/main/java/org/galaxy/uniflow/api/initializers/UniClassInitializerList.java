package org.galaxy.uniflow.api.initializers;

import org.jetbrains.annotations.NotNull;

public interface UniClassInitializerList {

    @NotNull UniClassInitializer @NotNull [] getInitializers();

    void addInitializer(@NotNull UniClassInitializer initializer);

    void removeInitializer(@NotNull UniClassInitializer initializer);

}
