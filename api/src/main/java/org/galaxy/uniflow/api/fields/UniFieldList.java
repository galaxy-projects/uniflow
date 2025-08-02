package org.galaxy.uniflow.api.fields;

import org.galaxy.uniflow.api.types.UniTypeName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniFieldList {

    @NotNull UniField @NotNull [] getFields();

    void addField(@NotNull UniField field);

    void removeField(@NotNull UniField field);

    void removeField(@NotNull UniTypeName name);

    @Nullable UniField getField(@NotNull UniTypeName name);

}
