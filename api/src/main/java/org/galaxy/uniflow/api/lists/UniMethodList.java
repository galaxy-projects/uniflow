package org.galaxy.uniflow.api.lists;

import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public interface UniMethodList {

    boolean isEmpty();

    @NotNull UniMethod @NotNull [] get();

    @NotNull Stream<@NotNull UniMethod> stream();

    void removeMethod(@NotNull UniMethodSignature signature);

    @NotNull UniMethod @NotNull [] getMethods(@NotNull String name);

    @Nullable UniMethod getMethod(@NotNull UniMethodSignature signature);

}
