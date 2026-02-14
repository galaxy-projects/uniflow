package org.galaxy.uniflow.common.factories;

import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.factories.UniFieldFinder;
import org.galaxy.uniflow.api.factories.UniTypeFactory;
import org.galaxy.uniflow.api.signatures.UniFieldSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class CommonFieldFinder implements UniFieldFinder {

    protected final UniTypeFactory parent;

    public CommonFieldFinder() {
        parent = Uniflow.getInstance().getTypeFactory();
    }

    @Override
    public @Nullable UniFieldSignature find(@NotNull Class<?> owner, @NotNull String name) {
        return find(parent.createClassType(owner), name);
    }

    @Override
    public @NotNull List<UniFieldSignature> find(@NotNull Class<?> owner, @NotNull UniType fieldType) {
        return find(parent.createClassType(owner), fieldType);
    }

    @Override
    public @NotNull List<UniFieldSignature> find(@NotNull UniClassType owner, @NotNull Class<?> fieldType) {
        return find(owner, parent.createClassType(fieldType));
    }

    @Override
    public @NotNull List<UniFieldSignature> find(@NotNull Class<?> owner, @NotNull Class<?> fieldType) {
        return find(parent.createClassType(owner), parent.createClassType(fieldType));
    }
}
