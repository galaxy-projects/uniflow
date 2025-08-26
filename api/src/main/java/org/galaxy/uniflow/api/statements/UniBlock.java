package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniBlock extends UniElement {

    boolean isStatic();

    @NotNull UniList<UniStatement> getStatements();

}
