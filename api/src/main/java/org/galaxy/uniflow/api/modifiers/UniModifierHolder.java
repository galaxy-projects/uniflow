package org.galaxy.uniflow.api.modifiers;

import org.jetbrains.annotations.NotNull;

public interface UniModifierHolder {

    @NotNull UniModifier @NotNull [] getModifiers();

    boolean hasModifier(@NotNull UniModifier modifier);

    void addModifier(@NotNull UniModifier modifier);

    void removeModifier(@NotNull UniModifier modifier);

}
