package org.galaxy.uniflow.api.factories;

import org.galaxy.uniflow.api.elements.imports.UniModuleImport;
import org.jetbrains.annotations.NotNull;

public interface UniJdk25ElementFactory extends UniJdk21ElementFactory {

    @NotNull UniModuleImport createModuleImport(@NotNull String moduleName);

}
