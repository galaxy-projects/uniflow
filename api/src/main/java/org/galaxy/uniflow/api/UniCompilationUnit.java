package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.elements.imports.UniImportBase;
import org.galaxy.uniflow.api.modules.UniModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniCompilationUnit extends UniElement {

    @Nullable UniModule getModule();

    @Nullable String getPackageName();

    @Nullable UniPackage getPackage();

    @NotNull UniList<@NotNull UniImportBase> getImports();

    @NotNull UniList<@NotNull UniClass> getClasses();

    @NotNull UniList<@NotNull UniElement> getOtherElements();

}
