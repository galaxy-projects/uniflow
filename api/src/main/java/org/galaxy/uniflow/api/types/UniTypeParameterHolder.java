package org.galaxy.uniflow.api.types;

import org.jetbrains.annotations.NotNull;

public interface UniTypeParameterHolder {

    @NotNull UniTypeParameter @NotNull [] getTypeParameters();

    boolean hasTypeParameter(@NotNull UniTypeName name);

    void addTypeParameter(@NotNull UniTypeParameter typeParameter);

    void removeTypeParameter(int index);

    void removeTypeParameter(@NotNull UniTypeParameter typeParameter);

    int getTypeParameterIndex(@NotNull UniTypeParameter typeParameter);

}
