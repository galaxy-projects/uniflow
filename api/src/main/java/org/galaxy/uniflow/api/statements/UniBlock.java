package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniBlock extends UniStatement {

    void setStatic(boolean isStatic);

    boolean isStatic();

    @NotNull UniList<UniStatement> getStatements();

}
