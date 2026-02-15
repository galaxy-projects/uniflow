package org.galaxy.uniflow.api.elements.imports;

import org.jetbrains.annotations.NotNull;

public interface UniStaticImport extends UniImportBase {

    void setTarget(@NotNull String className, @NotNull String qualifiedElement);

    @NotNull String getTargetClass();

    @NotNull String getTargetElement();

}
