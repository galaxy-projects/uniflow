package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;

public interface UniParameterList extends UniList<UniVariable> {

    default boolean hasParameters() {
        return !isEmpty();
    }

    boolean hasParameter(@NotNull String name);

    int getParameterIndex(@NotNull String name);

    void removeParameter(int index);

}
