package org.galaxy.uniflow.api.annotations;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.types.UniClassType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniAnnotationHolder extends UniElement {

    @NotNull UniList<@NotNull UniAnnotation> getAnnotations();

    @Nullable UniAnnotation getAnnotation(@NotNull UniClassType type);

    @Nullable UniAnnotation @NotNull [] getAllAnnotations(@NotNull UniClassType type);

    boolean hasAnnotation(@NotNull UniClassType type);

}
