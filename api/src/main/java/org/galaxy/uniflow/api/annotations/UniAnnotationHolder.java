package org.galaxy.uniflow.api.annotations;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.types.UniClassType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniAnnotationHolder extends UniElement {

    @NotNull UniAnnotation @NotNull [] getAnnotations();

    @Nullable UniAnnotation getAnnotation(@NotNull UniClassType type);

    @Nullable UniAnnotation @NotNull [] getAllAnnotations(@NotNull UniClassType type);

    boolean hasAnnotation(@NotNull UniClassType type);

    void addAnnotation(@NotNull UniAnnotation annotation);

    void removeAnnotation(@NotNull UniAnnotation annotation);

}
