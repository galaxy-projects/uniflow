package org.galaxy.uniflow.api.elements.imports;

import org.jetbrains.annotations.NotNull;

public interface UniImport extends UniImportBase {

    void setClasses(@NotNull String qualifiedElement);

    @NotNull String getClasses();

}
