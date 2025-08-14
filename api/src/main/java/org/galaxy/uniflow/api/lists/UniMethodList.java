package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniMethodList extends UniList<UniMethod> {

    default @NotNull UniMethod @NotNull [] getMethods() {
        return get();
    }

    void removeMethod(@NotNull UniMethodSignature signature);

    @NotNull UniMethod @NotNull [] getMethods(@NotNull String name);

    @Nullable UniMethod getMethod(@NotNull UniMethodSignature signature);

}
