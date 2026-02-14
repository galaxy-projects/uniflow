package org.galaxy.uniflow.common.factories;

import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.factories.UniMethodFinder;
import org.galaxy.uniflow.api.factories.UniTypeFactory;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class CommonMethodFinder implements UniMethodFinder {

    private final UniTypeFactory parent;

    public CommonMethodFinder() {
        parent = Uniflow.getInstance().getTypeFactory();
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull UniClassType owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType) {
        return find(owner, name, returnType, Collections.emptyList());
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull UniClassType owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull UniType[] parameterTypes) {
        return find(owner, name, returnType, Arrays.asList(parameterTypes));
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull Class<?> owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType) {
        return find(parent.createClassType(owner), name, returnType, Collections.emptyList());
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull Class<?> owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull UniType[] parameterTypes) {
        return find(parent.createClassType(owner), name, returnType, Arrays.asList(parameterTypes));
    }

    @Override
    public @Nullable UniMethodSignature find(@NotNull Class<?> owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull List<UniType> parameterTypes) {
        return find(parent.createClassType(owner), name, returnType, parameterTypes);
    }
}
