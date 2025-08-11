package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.jetbrains.annotations.NotNull;

public interface UniCatchList extends UniList<UniCatch> {

    default @NotNull UniCatch @NotNull [] getCatches() {
        return get();
    }

}
