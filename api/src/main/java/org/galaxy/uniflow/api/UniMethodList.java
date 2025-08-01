package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.methods.UniMethodSignature;
import org.galaxy.uniflow.api.types.UniTypeName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniMethodList {

    @NotNull UniMethod @NotNull [] getMethods();

    void addMethod(@NotNull UniMethod uniMethod);

    void removeMethod(@NotNull UniMethodSignature signature);

    void removeMethod(@NotNull UniMethod uniMethod);

    @NotNull UniMethod @NotNull [] getMethods(@NotNull UniTypeName name);

    @Nullable UniMethod getMethod(@NotNull UniMethodSignature signature);

}
