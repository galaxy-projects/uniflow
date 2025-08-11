package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.modules.UniModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniCompilationUnit extends UniElement {

    void setModule(@Nullable UniModule module);

    @Nullable UniModule getModule();

    @Nullable String getPackageName();

    @Nullable UniPackage getPackage();

    @NotNull UniList<UniImport> getImports();

    @NotNull UniList<UniElement> getDeclaredTypes();

    
}
