package org.galaxy.uniflow.api.statements;

import org.galaxy.uniflow.api.UniList;
import org.jetbrains.annotations.NotNull;

public interface UniCase extends UniCaseBase {

    @NotNull UniList<@NotNull UniStatement> getStatements();

}
