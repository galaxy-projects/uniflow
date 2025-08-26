package org.galaxy.uniflow.api.annotations;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.types.UniClassType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniAnnotation extends UniAnnotationValue, UniElement {

    @NotNull UniClassType getType();

    @NotNull UniAnnotationAttribute @NotNull [] getAttributes();

    boolean hasAttribute(@NotNull String name);

    @Nullable UniAnnotationValue getAttribute(@NotNull String name);

    void addAttribute(@NotNull String name, @NotNull UniAnnotationValue value);

    void addAttribute(@NotNull UniAnnotationAttribute attribute);

    void removeAttribute(@NotNull String name);

    void removeAttribute(@NotNull UniAnnotationAttribute attribute);

}
