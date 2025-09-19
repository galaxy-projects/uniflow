package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniFieldList extends UniList<UniVariable> {

    void removeField(@NotNull String name);

    @Nullable UniVariable getField(@NotNull String name);

}
