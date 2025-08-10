package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCatchList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UniTry extends UniStatement {

    void setBody(@NotNull UniBlock body);

    @NotNull UniBlock getBody();

    @NotNull UniCatchList getCatches();

    void setFinally(@Nullable UniBlock finallyBody);

    @Nullable UniBlock getFinallyBody();

    @NotNull UniList<UniElement> getResources();

}
