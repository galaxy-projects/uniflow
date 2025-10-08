package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniParameter;
import org.jetbrains.annotations.NotNull;

public interface UniParameterList extends UniList<UniParameter> {

    default boolean hasParameters() {
        return !isEmpty();
    }

    boolean hasParameter(@NotNull String name);

    int getParameterIndex(@NotNull String name);

}
