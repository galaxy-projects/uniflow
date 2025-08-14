package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniField;
import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniFieldList extends UniList<UniField> {

    default @NotNull UniField @NotNull [] getFields() {
        return get();
    }

    void removeField(@NotNull String name);

    @Nullable UniField getField(@NotNull String name);

}
