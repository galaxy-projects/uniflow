package org.galaxy.uniflow.api.elements.imports;

import org.jetbrains.annotations.NotNull;

public interface UniModuleImport extends UniImportBase {

    void setModuleName(@NotNull String moduleName);

    @NotNull String getModuleName();

}
