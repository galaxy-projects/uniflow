package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCase;
import org.jetbrains.annotations.NotNull;

public interface UniCaseList extends UniList<UniCase> {

    default @NotNull UniCase @NotNull [] getCases() {
        return get();
    }

}
