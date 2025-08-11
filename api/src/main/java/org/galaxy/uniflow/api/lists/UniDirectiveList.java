package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.jetbrains.annotations.NotNull;

public interface UniDirectiveList extends UniList<UniDirective> {

    default @NotNull UniDirective @NotNull [] getDirectives() {
        return get();
    }

}
