package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniClassInitializerList extends UniList<UniClassInitializer> {

    default @NotNull UniClassInitializer @NotNull [] getInitializers() {
        return get();
    }

}
