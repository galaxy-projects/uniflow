package org.galaxy.uniflow.api.elements;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniCatchList extends UniList<UniCatch> {

    default @NotNull UniCatch @NotNull [] getCatches() {
        return get();
    }

}
