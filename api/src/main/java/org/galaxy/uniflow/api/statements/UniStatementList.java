package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniStatementList extends UniList<UniStatement> {

    default @NotNull UniStatement @NotNull [] getStatements() {
        return get();
    }

}
