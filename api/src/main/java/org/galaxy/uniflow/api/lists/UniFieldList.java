package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.statements.UniField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniFieldList extends UniList<UniField> {

    void removeField(@NotNull String name);

    @Nullable UniField getField(@NotNull String name);

}
