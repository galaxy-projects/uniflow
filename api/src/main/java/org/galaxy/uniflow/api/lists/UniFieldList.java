package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.statements.UniField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public interface UniFieldList {

    boolean isEmpty();

    @NotNull Stream<@NotNull UniField> stream();

    @NotNull UniField @NotNull [] get();

    void removeField(@NotNull String name);

    @Nullable UniField getField(@NotNull String name);

}
